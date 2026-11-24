package com.chrisnkl.lmscourseservice.domain.exception.validation;

import com.chrisnkl.lmscourseservice.domain.exception.BackendException;

public class InputValidationException extends BackendException {
    public InputValidationException(String message) {
        super(message);
    }
}
