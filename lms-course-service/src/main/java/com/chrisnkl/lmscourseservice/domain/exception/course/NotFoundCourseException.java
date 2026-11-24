package com.chrisnkl.lmscourseservice.domain.exception.course;

import com.chrisnkl.lmscourseservice.domain.exception.BackendException;
import lombok.Getter;

@Getter
public class NotFoundCourseException extends BackendException {
    public NotFoundCourseException(String message) {
        super(message);
    }
}
