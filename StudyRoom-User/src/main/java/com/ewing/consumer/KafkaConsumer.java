package com.ewing.consumer;

import Utils.SnowUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ewing.WebSocket.WebSocketService;
import com.ewing.domain.dto.CheckInDto;
import com.ewing.domain.dto.MessageDto;
import com.ewing.domain.dto.NotificationDto;
import com.ewing.domain.dto.UserDto;
import com.ewing.domain.entity.NotificationsTable;
import com.ewing.domain.entity.UserTable;
import com.ewing.manager.RedisCache;
import com.ewing.mapper.NotificationsTableMapper;
import com.ewing.mapper.UserTableMapper;
import com.ewing.service.UserTableService;
import constant.CacheConstant;
import constant.SystemConfigConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import Exception.BusinessException;
import java.util.Map;

/**
 * @Author: Ewing
 * @Date: 2024-11-22-13:04
 * @Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final WebSocketService webSocketService;

    private final NotificationsTableMapper notificationsTableMapper;

    private final RedisCache redisCache;
    private final UserTableMapper userTableMapper;

    private final UserTableService userTableService;

    @KafkaListener(topics = SystemConfigConstant.REPLY_MESSAGE_TOPIC, groupId = SystemConfigConstant.MESSAGE_GROUP_ID)
    public void consumeReplyNotificationsMessage(ConsumerRecord<String, MessageDto> record) {
        MessageDto message = record.value();
        if (message == null) {
            log.warn("接收到空的回复消息");
            return;
        }

        // 获取目标用户ID（被回复的用户）
        String targetUserId = message.getReplyToUserId();
        if (targetUserId == null) {
            log.warn("消息未指定目标用户: {}", message);
            return;
        }

        log.info("接收到回复消息通知: {}", message);

        //记录通知到数据库
        NotificationDto notificationDto = saveNotificationToDatabase(message, targetUserId);

        // 推送通知给目标用户（示例：使用 WebSocket）
        webSocketService.sendMessageToUser(targetUserId, notificationDto);
    }

    @KafkaListener(topics = SystemConfigConstant.SYSTEM_MESSAGE_TOPIC, groupId = SystemConfigConstant.MESSAGE_GROUP_ID)
    public void consumeSystemNotificationsMessage(ConsumerRecord<String, Map<String, String>> record) {
        Map<String, String> message = record.value();
        if (message == null) {
            log.warn("接收到空的系统消息");
            return;
        }
        // 获取唯一的键值对
        Map.Entry<String, String> entry = message.entrySet().iterator().next();
        String targetUserId = entry.getKey();
        String content = entry.getValue();

        log.info("接收到系统消息通知: {}", message);

        //记录通知到数据库
        NotificationDto notificationDto = saveNotificationToDatabase(content, targetUserId);

        // 推送通知给所有用户（示例：使用 WebSocket）
        webSocketService.sendMessageToUser(targetUserId, notificationDto);

    }


    @KafkaListener(topics = SystemConfigConstant.USER_CREDIT_DEDUCTION_TOPIC, groupId = SystemConfigConstant.MESSAGE_GROUP_ID)
    public void consumeUserCreditDeductionNotificationsMessage(ConsumerRecord<String,Map<String, String>> record) throws BusinessException {
        Map<String, String> message = record.value();
        if (message == null) {
            log.warn("接收到空的系统消息");
            return;
        }

        // 获取唯一的键值对
        Map.Entry<String, String> entry = message.entrySet().iterator().next();
        String targetUserId = entry.getKey();
        String content = entry.getValue();

        //获取用户ID
        log.info("接收到系统内部消息通知：未在规定时间内签到的用户ID: {}", targetUserId);

        //获取用户信息
        QueryWrapper<UserTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", targetUserId);
        UserTable userInfo = userTableMapper.selectOne(queryWrapper);
        if (ObjectUtil.isEmpty(userInfo)) {
            log.warn("用户不存在，用户ID: {}", targetUserId);
            return;
        }

        UserTable updateUser = new UserTable();
        updateUser.setCreditScore(userInfo.getCreditScore() - 10);
        userTableMapper.update(updateUser, queryWrapper);

        // 从 Redis 中删除旧的缓存数据
        redisCache.deleteObject(CacheConstant.USERS_CACHE_KEY + targetUserId);

        // 将新的数据重新写入 Redis
        UserDto userDto = userTableService.getUserDtoById(targetUserId);
        redisCache.setCacheObject(CacheConstant.USERS_CACHE_KEY + targetUserId, userDto);

        //记录通知到数据库
        NotificationDto notificationDto = saveNotificationToDatabase(content, targetUserId);

        // 推送通知给用户
        webSocketService.sendMessageToUser(targetUserId, notificationDto);

    }

    @KafkaListener(topics = SystemConfigConstant.CHECK_IN_TOPIC, groupId = SystemConfigConstant.MESSAGE_GROUP_ID)
    public void consumeCheckInNotificationsMessage(ConsumerRecord<String, CheckInDto> record) {
        CheckInDto checkInDto = record.value();
        if (checkInDto == null) {
            log.warn("接收到空的回复消息");
            return;
        }

        // 获取目标用户ID（被回复的用户）
        String targetUserId = checkInDto.getUserId();
        if (targetUserId == null) {
            log.warn("消息未指定目标用户: {}", checkInDto);
            return;
        }

        log.info("接收到回复消息通知: {}", checkInDto);

        //记录通知到数据库
        NotificationDto notificationDto = saveNotificationToDatabase(checkInDto, targetUserId);

        // 推送通知给目标用户（示例：使用 WebSocket）
        webSocketService.sendMessageToUser(targetUserId, notificationDto);

    }
    /**
     * 记录通知到数据库
     *
     * @param msssage 回复消息
     * @param targetUserId 目标用户ID
     * @return 通知DTO
     * @author Ewing

     */
    private NotificationDto saveNotificationToDatabase(Object msssage, String targetUserId) {
        NotificationsTable notification = new NotificationsTable();
        notification.setNotificationId(SnowUtils.getSnowflakeNextIdStr());
        notification.setRecipientId(targetUserId);
        if (msssage instanceof MessageDto) {
            MessageDto msgDto = (MessageDto) msssage;
            notification.setTitle("回复通知");
            notification.setType("reply");
            notification.setContent(JSONUtil.toJsonStr(msgDto));
        } else if (msssage instanceof CheckInDto) {
            CheckInDto checkInDto = (CheckInDto) msssage;
            notification.setTitle("签到通知");
            notification.setType("check");
            notification.setContent(JSONUtil.toJsonStr(checkInDto));
        } else if (msssage instanceof String) {
            String content = (String) msssage;
            notification.setTitle("系统通知");
            notification.setType("system");
            notification.setContent(content);
        } else {
            log.warn("未知的消息类型: {}", msssage.getClass().getName());
            notification.setContent(JSONUtil.toJsonStr(msssage));
        }
        notification.setStatus(SystemConfigConstant.NOTIFICATION_UNREAD);
        notification.setCreatedAt(new DateTime());
        notification.setUpdatedAt(new DateTime());
        // 持久化到数据库
        notificationsTableMapper.insert(notification);
        NotificationDto notificationDto = BeanUtil.copyProperties(notification, NotificationDto.class);
        return notificationDto;
    }
}
