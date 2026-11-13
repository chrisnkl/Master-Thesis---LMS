package com.chrisnkl.langfunc.domain;

import com.chrisnkl.langfunc.exception.UnsupportedLanguageException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SupportedLanguage {

    ENGLISH("en"),
    GREEK("el");

    private final String languageCode;

    public static SupportedLanguage findByLanguageCode(String languageCode) {
        for (SupportedLanguage lang : SupportedLanguage.values()) {
            if (lang.getLanguageCode().equalsIgnoreCase(languageCode.toLowerCase())) {
                return lang;
            }
        }
        throw new UnsupportedLanguageException("Unsupported language code: " + languageCode);
    }

}
