package com.ewing.WebSocket;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ewing.domain.dto.MessageDto;
import com.ewing.domain.dto.NotificationDto;
import com.ewing.domain.entity.NotificationsTable;
import com.ewing.mapper.NotificationsTableMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 服务
 * @author ewing
 */
@Component
@Slf4j
@ServerEndpoint("/ws/{userId}")
public class WebSocketService {
//
//    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
//
//    // 连接建立时记录用户会话
//    public void addSession(String userId, WebSocketSession session) {
//        userSessions.put(userId, session);
//        log.info("用户 {} 已连接 WebSocket", userId);
//    }
//
//    // 连接关闭时移除用户会话
//    public void removeSession(String userId) {
//        userSessions.remove(userId);
//        log.info("用户 {} 的 WebSocket 已断开", userId);
//    }
//
//    // 发送消息到指定用户
//    public void sendMessageToUser(String userId, String message) {
//        WebSocketSession session = userSessions.get(userId);
//        if (session != null && session.isOpen()) {
//            try {
//                session.sendMessage(new TextMessage(message));
//                log.info("消息已发送到用户 {}: {}", userId, message);
//            } catch (IOException e) {
//                log.error("发送消息失败，用户ID: {}, 消息: {}", userId, message, e);
//            }
//        } else {
//            log.warn("用户 {} 不在线，无法发送消息", userId);
//        }
//    }
    private static final Map<String, Session> userSessions = new ConcurrentHashMap<>();

    //  这里使用静态，让 service 属于类
    private static NotificationsTableMapper notificationsTableMapper;

    @Autowired
    public void setNotificationsTableMapper(NotificationsTableMapper notificationsTableMapper) {
        WebSocketService.notificationsTableMapper = notificationsTableMapper;
    }
    private String userId;

    /**
     * 建立连接时调用
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.userId = userId;
        userSessions.put(userId, session);
        log.info("用户 {} 建立 WebSocket 连接，当前在线人数: {}", userId, userSessions.size());

        // 获取并推送未读通知
        QueryWrapper<NotificationsTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recipient_id", userId);
        queryWrapper.eq("status", "0");
        List<NotificationsTable> unreadNotifications = notificationsTableMapper.selectList(queryWrapper);
        List<NotificationDto> notificationDtos = BeanUtil.copyToList(unreadNotifications, NotificationDto.class);
        for (NotificationDto notification : notificationDtos) {
            try {
                session.getBasicRemote().sendText(JSONUtil.toJsonStr(notification));
                log.info("推送未读通知到用户 {}: {}", userId, notification);
            } catch (IOException e) {
                log.error("推送未读通知失败，用户ID: {}, 消息: {}", userId, notification, e);
            }
        }
    }

    /**
     * 连接关闭时调用
     */
    @OnClose
    public void onClose() {
        userSessions.remove(userId);
        log.info("用户 {} 断开 WebSocket 连接，当前在线人数: {}", userId, userSessions.size());
    }

    /**
     * 收到消息时调用
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("收到用户 {} 的消息: {}", userId, message);
        // 解析消息
        Map<String, Object> messageMap = JSONUtil.parseObj(message);
        String type = (String) messageMap.get("type");
        String noticeId = (String) messageMap.get("noticeId");

        // 根据消息类型处理消息
        if ("markAsRead".equals(type)) {
            markAsRead(noticeId);
        }else if ("heartbeat".equals(type)){
            log.info("收到用户 {} 的心跳消息", userId);
        } else {
            log.warn("未知的消息类型: {}", type);
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WebSocket 发生错误，用户: {}", userId, error);
    }

    /**
     * 发送消息到指定用户
     */
    public void sendMessageToUser(String targetUserId, NotificationDto message) {
        Session session = userSessions.get(targetUserId);
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(JSONUtil.toJsonStr(message));
                log.info("消息已发送到用户 {}: {}", targetUserId, message);
            } catch (IOException e) {
                log.error("发送消息失败，用户ID: {}, 消息: {}", targetUserId, message, e);
            }
        } else {
            log.warn("用户 {} 不在线，无法发送消息,已转存消息通知到数据库", targetUserId);
        }
    }

    /**
     * 发送消息到所有在线用户
     */
    public static void broadcast(String message) {
        userSessions.forEach((userId, session) -> {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                    log.info("广播消息到用户 {}: {}", userId, message);
                } catch (IOException e) {
                    log.error("广播消息失败，用户ID: {}, 消息: {}", userId, message, e);
                }
            }
        });
    }

    private void markAsRead(String noticeId) {
        try {
            // 构建查询条件
            UpdateWrapper<NotificationsTable> queryWrapper = new UpdateWrapper<>();
            queryWrapper.eq("notification_id", noticeId);

            // 更新状态
            NotificationsTable update = new NotificationsTable();
            update.setStatus("1");
            // 更新状态
            int rowsUpdated = notificationsTableMapper.update(update, queryWrapper);

            if (rowsUpdated > 0) {
                log.info("通知 {} 已成功标记为已读", noticeId);
            } else {
                log.warn("通知 {} 标记为已读失败，可能不存在该通知", noticeId);
            }
        } catch (Exception e) {
            log.error("标记通知为已读时发生错误，通知ID: {}", noticeId, e);
        }
    }

}
