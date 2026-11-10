package com.chrisnkl.langfunc.exception;

import com.chrisnkl.libs.exception.FunctionHttpException;
import com.microsoft.azure.functions.HttpStatus;

public class UnsupportedLanguageException extends FunctionHttpException {
    public UnsupportedLanguageException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value());
    }
}
