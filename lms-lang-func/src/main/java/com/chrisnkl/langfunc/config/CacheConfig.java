package com.chrisnkl.langfunc.config;

import com.chrisnkl.langfunc.app.AppKey;

public record CacheConfig(

        String host,
        int port,
        int ttl,
        String user,
        String password,
        boolean ssl
) {

    public static CacheConfig create() {
        String host = AppKey.REDIS_HOST.getValue();
        int port = Integer.parseInt(AppKey.REDIS_PORT.getValue());
        int ttl = Integer.parseInt(AppKey.REDIS_TTL.getValue());

        String user = AppKey.REDIS_USER.getValue();
        String password = AppKey.REDIS_PASSWORD.getValue();
        boolean ssl = Boolean.parseBoolean(AppKey.REDIS_SSL.getValue());

        return new CacheConfig(host, port, ttl, user, password, ssl);
    }

}
