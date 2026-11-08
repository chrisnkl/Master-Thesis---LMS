package com.chrisnkl.langfunc.factory;

import com.chrisnkl.langfunc.handler.ILanguageHandler;
import com.chrisnkl.langfunc.handler.LanguageHandler;

public class ServiceFactory {

    private static ILanguageHandler iLanguageHandler;

    public static ILanguageHandler getLanguageHandler() {
        if (iLanguageHandler == null) {
            iLanguageHandler = new LanguageHandler();
        }
        return iLanguageHandler;
    }

}
