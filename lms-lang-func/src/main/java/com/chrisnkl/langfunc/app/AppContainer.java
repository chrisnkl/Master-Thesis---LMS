package com.chrisnkl.langfunc.app;

import com.chrisnkl.langfunc.handler.LanguageHandler;
import com.chrisnkl.langfunc.handler.DefaultLanguageHandler;

public class AppContainer {

    private static final LanguageHandler LANGUAGE_HANDLER;

    static {
        LANGUAGE_HANDLER = new DefaultLanguageHandler();
    }

    private AppContainer() {}

    public static LanguageHandler getLanguageHandler() {
        return LANGUAGE_HANDLER;
    }

}
