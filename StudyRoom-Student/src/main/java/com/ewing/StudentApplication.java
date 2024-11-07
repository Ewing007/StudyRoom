//package com.ewing;
//
//import lombok.extern.slf4j.Slf4j;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.core.env.ConfigurableEnvironment;
//
///**
// * @Author: Ewing
// * @Date: 2024-09-30-23:47
// * @Description:
// */
//@Slf4j
//@SpringBootApplication
//@MapperScan("com.ewing.mapper")
//public class StudentApplication {
//
//    public static void main(String[] args) {
//        ConfigurableApplicationContext applicationContext = SpringApplication.run(StudentApplication.class);
//        ConfigurableEnvironment environment = applicationContext.getEnvironment();
//        String port = environment.getProperty("server.port");
//        log.info("服务启动...");
//        log.info("服务启动成功 http://localhost:{}", port);
//    }
//}
