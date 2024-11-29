//package com.ewing.handler;
//
//import com.ewing.WebSocket.WebSocketService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.net.URI;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @Author: Ewing
// * @Date: 2024-11-23-17:46
// * @Description:
// */
//
//@RequiredArgsConstructor
//@Slf4j
//public class WebSocketHandler extends TextWebSocketHandler {
//
//    private final WebSocketService webSocketService;
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        URI uri = session.getUri();
//        List<String> pathSegments = List.of(uri.getPath().split("/")).stream()
//                .filter(segment -> !segment.isEmpty())
//                .collect(Collectors.toList());
//        // 从路径中提取用户ID
//        String userId = pathSegments.get(pathSegments.size() - 1);
//        // 存储 WebSocket 会话
//        log.info("WebSocket 连接成功, userId: {}, uri: {}", userId, uri);
//        webSocketService.addSession(userId, session);
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        // 处理收到的消息
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        URI uri = session.getUri();
//        List<String> pathSegments = List.of(uri.getPath().split("/")).stream()
//                .filter(segment -> !segment.isEmpty())
//                .collect(Collectors.toList());
//        String userId = pathSegments.get(pathSegments.size() - 1);
//        // 移除 WebSocket 会话
//        webSocketService.removeSession(userId);
//    }
//}
