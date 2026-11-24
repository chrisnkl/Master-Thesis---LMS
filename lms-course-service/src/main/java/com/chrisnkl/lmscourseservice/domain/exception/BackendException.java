package com.chrisnkl.lmscourseservice.domain.exception;

import lombok.Getter;

@Getter
public class BackendException extends RuntimeException {
    public BackendException(String message) {
        super(message);
    }
}
