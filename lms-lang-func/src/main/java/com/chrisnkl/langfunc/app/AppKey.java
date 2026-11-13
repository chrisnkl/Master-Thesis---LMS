package com.chrisnkl.langfunc.app;

import com.chrisnkl.libs.exception.impl.AppKeyMissingException;

public enum AppKey {

    REDIS_HOST,
    REDIS_PORT,
    REDIS_TTL;

    public String getValue() {
        return getEnvironmentVariable(this.name().toUpperCase());
    }

    public static String getEnvironmentVariable(String key) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            value = System.getProperty(key);
        }
        if (value == null || value.isBlank()) throw new AppKeyMissingException(key);
        return value;
    }

}
