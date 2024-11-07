//package com.ewing;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.core.env.ConfigurableEnvironment;
//
///**
// * @Author: Ewing
// * @Date: 2024-10-07-22:35
// * @Description:
// */
//@Slf4j
//@SpringBootApplication
//public class TeacherApplication {
//    public static void main(String[] args) {
//        ConfigurableApplicationContext applicationContext = SpringApplication.run(TeacherApplication.class);
//        ConfigurableEnvironment environment = applicationContext.getEnvironment();
//        String port = environment.getProperty("server.port");
//        log.info("服务启动...");
//        log.info("服务启动成功 http://localhost:{}", port);
//    }
//}
