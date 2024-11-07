package com.ewing;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Author: Ewing
 * @Date: 2024-09-30-12:37
 * @Description:
 */
@Slf4j
@SuppressWarnings("ALL")
@SpringBootApplication
@MapperScan(basePackages = "com.ewing.mapper")
@EnableFeignClients
public class RoomApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(RoomApplication.class);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String port = environment.getProperty("server.port");
        log.info("启动服务...");
        log.info("服务启动成功: http://localhost:{}",port);

    }
}
