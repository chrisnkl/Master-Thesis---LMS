package com.chrisnkl.langfunc.app;

import com.chrisnkl.langfunc.config.CacheConfig;
import com.chrisnkl.langfunc.handler.LanguageHandler;
import com.chrisnkl.langfunc.handler.DefaultLanguageHandler;
import com.chrisnkl.langfunc.helper.RedisHelper;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.RedisClient;
import redis.clients.jedis.SslOptions;
import tools.jackson.databind.ObjectMapper;

public class AppContainer {

    private static final ObjectMapper OBJECT_MAPPER;
    private static final RedisHelper REDIS_HELPER;
    private static final LanguageHandler LANGUAGE_HANDLER;


    static {

        CacheConfig cacheConfig = CacheConfig.create();
        DefaultJedisClientConfig.Builder clientConfigBuilder = DefaultJedisClientConfig.builder();

        if (cacheConfig.user() != null && !cacheConfig.user().isBlank()) {
            clientConfigBuilder.user(cacheConfig.user());
        }

        if (cacheConfig.password() != null && !cacheConfig.password().isBlank()) {
            clientConfigBuilder.password(cacheConfig.password());
        }

        if (cacheConfig.ssl()) {
            clientConfigBuilder.sslOptions(SslOptions.builder().build());
        }

        OBJECT_MAPPER = new ObjectMapper();
        REDIS_HELPER = new RedisHelper(cacheConfig, RedisClient.builder()
                .hostAndPort(cacheConfig.host(), cacheConfig.port())
                .clientConfig(clientConfigBuilder.build())
                .build());
        LANGUAGE_HANDLER = new DefaultLanguageHandler();
    }

    private AppContainer() {}

    public static ObjectMapper getObjectMapper() { return OBJECT_MAPPER; }
    public static RedisHelper getRedisHelper() { return REDIS_HELPER; }
    public static LanguageHandler getLanguageHandler() {
        return LANGUAGE_HANDLER;
    }

}
