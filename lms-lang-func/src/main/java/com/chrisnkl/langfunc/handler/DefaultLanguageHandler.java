package com.chrisnkl.langfunc.handler;

import com.chrisnkl.langfunc.app.AppContainer;
import com.chrisnkl.langfunc.domain.SupportedLanguage;
import com.chrisnkl.langfunc.exception.LanguageException;
import com.chrisnkl.langfunc.exception.LanguageMissingException;
import com.chrisnkl.langfunc.exception.UnsupportedLanguageException;
import com.chrisnkl.langfunc.helper.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.RedisClient;
import redis.clients.jedis.params.SetParams;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
public class DefaultLanguageHandler implements LanguageHandler {

    private static final Map<SupportedLanguage, Map<String, String>> MEMORY_CACHE = new EnumMap<>(SupportedLanguage.class);
    private final ObjectMapper objectMapper = AppContainer.getObjectMapper();
    private final RedisHelper redisHelper = AppContainer.getRedisHelper();
    private final String CACHE_KEY = "i18n:";

    private Map<String, String> loadFromCache(SupportedLanguage language) {

        final String key = CACHE_KEY + language.getLanguageCode().toLowerCase();

        try {

            Map<String, String> cachedTranslations = MEMORY_CACHE.get(language);
            if (cachedTranslations != null) return cachedTranslations;

            String value = redisHelper.getRedisClient().get(key);
            if (value != null && !value.isBlank()) {

                cachedTranslations = objectMapper.readValue(value, new TypeReference<Map<String, String>>() {});
                MEMORY_CACHE.put(language, cachedTranslations);

                return cachedTranslations;
            }
        } catch (JacksonException e) {
            log.error("Error parsing translations from redis for language code: {}", language.getLanguageCode(), e);
        } catch (Exception e) {
            log.error("Error retrieving translations from redis for language code: {}", language.getLanguageCode(), e);
        }

        return null;
    }

    @Override
    public Map<String, String> getTranslations(SupportedLanguage language) {
        if (language == null) throw new UnsupportedLanguageException("Language cannot be null.");

        log.info("Fetching translations for language code: {}", language.getLanguageCode());

        final String key = CACHE_KEY + language.getLanguageCode().toLowerCase();
        Map<String, String> cachedTranslations = loadFromCache(language);

        if (cachedTranslations != null) {
            log.info("Fetched cached translations for language code: {}", language.getLanguageCode());
            return cachedTranslations;
        }

        final String resourcePath = "/i18n/" + language.getLanguageCode() + ".json";
        log.info("Loading translations from resource path: {}", resourcePath);

        try(InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {

            if (inputStream == null) throw new LanguageMissingException("Translation file not found for language code: " + language.getLanguageCode());

            Map<String, String> translations =  objectMapper.readValue(inputStream, new TypeReference<Map<String, String>>() {});
            String value = objectMapper.writeValueAsString(translations);

            // Cache the translations
            MEMORY_CACHE.put(language, translations);
            if (redisHelper.getRedisClient() != null) {
                try {
                    redisHelper.getRedisClient().set(key, value, SetParams.setParams().ex(redisHelper.getCacheConfig().ttl()));
                } catch (Exception e) {
                    log.error("Failed to cache the translations to redis for language code: {}", language.getLanguageCode());
                }
            }

            return translations;

        } catch (JacksonException e) {
            log.error("Error parsing translation file for language code: {}", language.getLanguageCode(), e);
            throw new LanguageException("Failed to parse translation file for language code: " + language.getLanguageCode());
        } catch (LanguageMissingException e) {
            log.error("Translation file was not found for language code: {}", language.getLanguageCode());
            throw e;
        } catch (Exception e) {
            log.error("Error loading translations for language code: {}", language.getLanguageCode(), e);
            throw new LanguageException("Failed to load translations for language code: " + language.getLanguageCode());
        }
    }
}
