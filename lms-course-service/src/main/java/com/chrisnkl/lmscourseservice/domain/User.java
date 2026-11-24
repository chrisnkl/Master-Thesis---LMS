package com.chrisnkl.lmscourseservice.domain;

import com.chrisnkl.lmscourseservice.domain.exception.validation.InputValidationException;

public record User(
        String id,
        String email,
        String firstName,
        String lastName
) {

    public String getFullName() {
        if (email == null) throw new InputValidationException("Invalid 'email' parameter.");
        return (firstName != null && lastName != null) ? firstName + " " + lastName: email;
    }
}
