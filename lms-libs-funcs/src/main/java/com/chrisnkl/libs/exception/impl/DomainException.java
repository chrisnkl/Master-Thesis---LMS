package com.chrisnkl.libs.exception.impl;

import com.chrisnkl.libs.exception.FunctionHttpException;
import com.microsoft.azure.functions.HttpStatus;

public class DomainException extends FunctionHttpException {
    public DomainException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value());
    }
}
