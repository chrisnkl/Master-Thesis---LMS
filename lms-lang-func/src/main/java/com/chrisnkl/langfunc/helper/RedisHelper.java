package com.chrisnkl.langfunc.helper;

import com.chrisnkl.langfunc.config.CacheConfig;
import lombok.Getter;
import redis.clients.jedis.RedisClient;

@Getter
public class RedisHelper {

    private final CacheConfig cacheConfig;
    private final RedisClient redisClient;

    public RedisHelper(CacheConfig cacheConfig, RedisClient redisClient) {
        this.cacheConfig = cacheConfig;
        this.redisClient = redisClient;
    }

}
