package com.ewing.config;
import com.ewing.WebSocket.WebSocketService;
//import com.ewing.handler.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * @Author: Ewing
 * @Date: 2024-11-23-17:44
 * @Description:
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter()
    {
        return new ServerEndpointExporter();
    }

}

//public class WebSocketConfig implements WebSocketConfigurer {
//    private final WebSocketService webSocketService;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        // WebSocket 服务路径
//        registry.addHandler(webSocketHandler(), "/ws/{userId}")
//                // 配置 WebSocket 握手拦截器
//                .addInterceptors(handshakeInterceptor())
//                // 允许的跨域请求
//                .setAllowedOrigins("*");
//    }
//
//    @Bean
//    public WebSocketHandler webSocketHandler() {
//        // 将 WebSocket 服务注入到 handler 中
//        return new WebSocketHandler(webSocketService);
//    }
//
//    private HandshakeInterceptor handshakeInterceptor() {
//        // 使用 HttpSession 握手拦截器
//        return new HttpSessionHandshakeInterceptor();
//    }
//
//}