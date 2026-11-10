package com.chrisnkl.langfunc.exception;

import com.chrisnkl.libs.exception.FunctionException;

public class LanguageMissingException extends FunctionException {
    public LanguageMissingException(String message) {
        super(message);
    }
}
