package com.example.demo;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@ComponentScan
public class redissonConfig {

   @Value("redis://127.0.0.1:6379")
    private String redisAddress;
    public static final int REDIS_CONNECT_TIMEOUT=300;
    public static final int REDIS_RETRY_ATTEMPTS=50;
    public static final int REDIS_DNS_MONITORING_INTERVAL=300;

    @Bean
    public RedissonClient redissonClient() {
        final Config redissonConfig = new Config();
        redissonConfig.useSingleServer()
                .setKeepAlive(true)
                .setConnectTimeout(REDIS_CONNECT_TIMEOUT)
                .setRetryAttempts(REDIS_RETRY_ATTEMPTS)
                .setDnsMonitoringInterval(REDIS_DNS_MONITORING_INTERVAL)
                .setAddress(redisAddress);
        return Redisson.create(redissonConfig);
    }




    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) throws IOException {

        Codec codec = new JsonJacksonCodec();
        Map<String, CacheConfig> config = new HashMap<String,CacheConfig>();
        //the ttl should be multiplied by 1000,at leat 1000 which equals one second
        CacheConfig empConf= new CacheConfig(5*60*1000,60*1000);
        config.put("employeesCache", empConf);
        RedissonSpringCacheManager manager= new RedissonSpringCacheManager(redissonClient, config,codec);
        return manager;
    }
}
