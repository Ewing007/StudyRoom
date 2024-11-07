//package com.ewing.config;
//
//
//import context.UserContext;
//import context.UserInfoContextHandler;
//import feign.RequestInterceptor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Author: Ewing
// * @Date: 2024-10-16-12:22
// * @Description:
// */
//@Configuration
//@Slf4j
//public class FeignConfig {
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return requestTemplate -> {
//            UserContext context = UserInfoContextHandler.getUserContext();
//            log.info("feign请求头信息：{}", context.getPermissions());
//            requestTemplate.header("userId", context.getUserId());
//            requestTemplate.header("username", context.getUserName());
//            requestTemplate.header("permission", context.getPermissions());
//            // 添加其他必要的认证信息
//        };
//    }
//}
