package com.ewing.config;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-11-19-19:22
 * @Description:
 */
@Configuration
public class RedissonConfig {

    @Value("${redisson.single-servers}")
    private List<String> serverAddresses;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setPassword("200103"); // 设置Redis密码
        return Redisson.create(config);
    }

    @Bean
    public RedlockService redlockService(RedissonClient redissonClient) {
        return new RedlockService(redissonClient);
    }
}

