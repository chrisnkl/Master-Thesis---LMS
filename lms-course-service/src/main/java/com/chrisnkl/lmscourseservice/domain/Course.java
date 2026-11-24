package com.chrisnkl.lmscourseservice.domain;

import com.chrisnkl.lmscourseservice.domain.exception.validation.InputValidationException;

import java.time.Instant;
import java.util.Set;
import java.util.regex.Pattern;

public record Course(

        String id,
        String code,
        String title,
        String summary,
        String ownedBy,
        Set<String> assistants,
        Set<Assignment> assignments,
        Set<CourseActionLog> logs,
        Instant createdAt,
        Instant updatedAt


){

    public static final String CODE_REGEX = "^[A-Z]{5}-[0-9]{2}$";
    private static final Pattern CODE_PATTERN = Pattern.compile(CODE_REGEX);

    public Course {
        if (!isValidCode(code)) throw new InputValidationException("Invalid 'code' pattern.");
        if (title == null || title.isBlank()) throw new InputValidationException("Invalid 'title' parameter.");

        assistants = assistants != null ? Set.copyOf(assistants) : Set.of();
        assignments = assignments != null ? Set.copyOf(assignments) : Set.of();
        logs = logs != null ? Set.copyOf(logs) : Set.of();

    }

    public static boolean isValidCode(String code) {
        return code != null && !code.isBlank() && CODE_PATTERN.matcher(code).matches();
    }

    public static Course create(
            String code,
            String title,
            String summary,
            String ownedBy
    ) {

        Instant now = Instant.now();

        return new Course(
                null,
                code,
                title,
                summary,
                ownedBy,
                Set.of(),
                Set.of(),
                Set.of(),
                now,
                now
        );

    }

}
