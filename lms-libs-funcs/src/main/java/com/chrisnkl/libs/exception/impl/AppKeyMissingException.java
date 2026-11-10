package com.chrisnkl.libs.exception.impl;

import com.chrisnkl.libs.exception.FunctionHttpException;
import com.microsoft.azure.functions.HttpStatus;

public class AppKeyMissingException extends FunctionHttpException {
    public AppKeyMissingException(String key) {
        super(String.format("Environment variable '%s' is missing or blank.", key), HttpStatus.INTERNAL_SERVER_ERROR.value());;
    }
}
