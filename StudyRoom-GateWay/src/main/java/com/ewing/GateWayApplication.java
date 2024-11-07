package com.ewing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Slf4j
public class GateWayApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GateWayApplication.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();
        String port = environment.getProperty("server.port");
        log.info("服务启动...");
        log.info("服务启动成功 http://localhost:{}", port);
    }

}
