package com.chrisnkl.lmscourseservice.application.port.in;

import com.chrisnkl.lmscourseservice.domain.Course;
import com.chrisnkl.lmscourseservice.domain.exception.course.CourseCreationException;
import com.chrisnkl.lmscourseservice.domain.exception.validation.InputValidationException;

public interface CreateCoursesUseCase {

    Course create(CreateCourseCommand command) throws CourseCreationException, InputValidationException;

    record CreateCourseCommand(

            String code,
            String title,
            String summary,
            String ownedBy

    ) {

        public CreateCourseCommand {
            if (code == null || code.isBlank()) throw new InputValidationException("Invalid 'code' parameter.");
            if (!code.matches("[A-Z]{5}-[0-9]{2}")) throw new InputValidationException("Invalid 'code' pattern.");
            if (title == null || title.isBlank()) throw new InputValidationException("Invalid 'title' parameter.");
            if (ownedBy == null || ownedBy.isBlank()) throw new InputValidationException("Invalid 'ownedBy' parameter.");
        }

    }

}
