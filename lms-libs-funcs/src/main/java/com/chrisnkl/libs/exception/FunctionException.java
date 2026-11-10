package com.chrisnkl.libs.exception;

import lombok.Getter;

@Getter
public class FunctionException extends RuntimeException {
    public FunctionException(String message) {
        super(message);
    }
}
