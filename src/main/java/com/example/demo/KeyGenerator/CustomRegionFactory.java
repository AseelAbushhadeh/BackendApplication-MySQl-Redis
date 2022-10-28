package com.example.demo.KeyGenerator;

import org.hibernate.cache.spi.CacheKeysFactory;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.hibernate.RedissonRegionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class CustomRegionFactory extends RedissonRegionFactory{

    final protected CustomCacheKeysFactory keysFactory=CustomCacheKeysFactory.INSTANCE;
    @Autowired
    final protected RedissonClient customRedissonClient;

    public CustomRegionFactory() {
        customRedissonClient = redissonClient();
    }


    public RedissonClient redissonClient() {
        final Config redissonConfig = new Config();
        redissonConfig.useSingleServer()
                .setKeepAlive(true)
                .setConnectTimeout(1000*3)
                .setRetryAttempts(5)
                .setDnsMonitoringInterval(1000*8)
                .setAddress("redis://127.0.0.1:6379");
        return Redisson.create(redissonConfig);
    }
    @Override
    protected RedissonClient createRedissonClient(Map properties) {
        return customRedissonClient;
    }

    @Override
    protected CacheKeysFactory getImplicitCacheKeysFactory() {
        return keysFactory;
    }
}
