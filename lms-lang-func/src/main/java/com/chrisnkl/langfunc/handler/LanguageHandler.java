package com.chrisnkl.langfunc.handler;

import com.chrisnkl.langfunc.domain.SupportedLanguage;

import java.util.Map;

public interface LanguageHandler {

    Map<String, String> getTranslations(SupportedLanguage language);

}
