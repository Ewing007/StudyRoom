package com.ewing.config;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

public class RedlockService {

    private final RedissonClient redissonClient;

    public RedlockService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public boolean lock(String key, long leaseTime, java.util.concurrent.TimeUnit unit) {
        RLock lock = redissonClient.getLock(key);
        try {
            return lock.tryLock(leaseTime, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public void unlock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }
}

