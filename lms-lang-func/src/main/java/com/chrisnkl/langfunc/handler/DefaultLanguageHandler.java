package com.chrisnkl.langfunc.handler;

import com.chrisnkl.langfunc.app.AppContainer;
import com.chrisnkl.langfunc.domain.SupportedLanguage;
import com.chrisnkl.langfunc.exception.LanguageException;
import com.chrisnkl.langfunc.exception.LanguageMissingException;
import com.chrisnkl.langfunc.exception.UnsupportedLanguageException;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Map;

@Slf4j
public class DefaultLanguageHandler implements LanguageHandler {

    private final ObjectMapper objectMapper = AppContainer.getObjectMapper();

    // TODO: To implement external caching mechanism for translations, e.g., Redis.
    private Map<String, String> isCached() {
        return null;
    }

    @Override
    public Map<String, String> getTranslations(SupportedLanguage language) {
        if (language == null) throw new UnsupportedLanguageException("Language cannot be null.");

        log.info("Fetching translations for language code: {}", language.getLanguageCode());

        final String resourcePath = "/i18n/" + language.getLanguageCode() + ".json";
        log.info("Loading translations from resource path: {}", resourcePath);

        try(InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {

            if (inputStream == null)
                throw new LanguageMissingException("Translation file not found for language code: " + language.getLanguageCode());
            return objectMapper.readValue(inputStream, new TypeReference<Map<String, String>>() {
            });

        } catch (JacksonException e) {
            log.error("Error parsing translation file for language code: {}", language.getLanguageCode(), e);
            throw new LanguageException("Failed to parse translation file for language code: " + language.getLanguageCode());
        } catch (Exception e) {
            log.error("Error loading translations for language code: {}", language.getLanguageCode(), e);
            throw new LanguageException("Failed to load translations for language code: " + language.getLanguageCode());
        }
    }
}
