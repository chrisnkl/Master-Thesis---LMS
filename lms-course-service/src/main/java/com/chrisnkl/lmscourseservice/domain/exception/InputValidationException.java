package com.chrisnkl.lmscourseservice.domain.exception;

public class InputValidationException extends RuntimeException {
    public InputValidationException(String message) {
        super(message);
    }
}
