package com.chrisnkl.langfunc.config;

import com.chrisnkl.langfunc.app.AppKey;

public record CacheConfig(

        String host,
        int port,
        int ttl
) {

    public static CacheConfig create() {
        String host = AppKey.REDIS_HOST.getValue();
        int port = Integer.parseInt(AppKey.REDIS_PORT.getValue());
        int ttl = Integer.parseInt(AppKey.REDIS_TTL.getValue());
        return new CacheConfig(host, port, ttl);
    }

}
