package com.chrisnkl.libs.exception;

import lombok.Getter;

@Getter
public class FunctionHttpException extends FunctionException {

    private final int status;

    public FunctionHttpException(String message, int status) {
        super(message);
        this.status = status;
    }
}
