package com.chrisnkl.lmscourseservice.domain;

import java.time.Instant;
import java.util.Set;

public record Course(

        String id,
        String code,
        String title,
        String summary,
        User ownedBy,
        Set<User> assistants,
        Set<Assignment> assignments,
        Set<CourseActionLog> logs,
        Instant createdAt,
        Instant updatedAt


){ }
