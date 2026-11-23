package com.chrisnkl.lmscourseservice.domain;

import java.time.Instant;

public record CourseActionLog(

        String id,
        CourseAction courseAction,
        String userId,
        Instant createdAt


) {

    public enum CourseAction {

        CHANGE_SUMMARY,

        ADD_ATTACHMENT,
        REMOVE_ATTACHMENT,

        CREATE_ASSIGNMENT,
        DELETE_ASSIGNMENT,

        CREATE_ANNOUNCEMENT
    }

}
