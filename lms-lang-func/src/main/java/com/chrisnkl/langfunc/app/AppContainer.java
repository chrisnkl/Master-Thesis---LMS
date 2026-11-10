package com.chrisnkl.langfunc.app;

import com.chrisnkl.langfunc.handler.LanguageHandler;
import com.chrisnkl.langfunc.handler.DefaultLanguageHandler;
import tools.jackson.databind.ObjectMapper;

public class AppContainer {

    private static final ObjectMapper OBJECT_MAPPER;
    private static final LanguageHandler LANGUAGE_HANDLER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        LANGUAGE_HANDLER = new DefaultLanguageHandler();
    }

    private AppContainer() {}

    public static ObjectMapper getObjectMapper() { return OBJECT_MAPPER; }
    public static LanguageHandler getLanguageHandler() {
        return LANGUAGE_HANDLER;
    }

}
