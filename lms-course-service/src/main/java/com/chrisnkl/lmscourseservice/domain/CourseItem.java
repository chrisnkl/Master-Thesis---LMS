package com.chrisnkl.lmscourseservice.domain;

import java.time.Instant;

public record CourseItem(

        String id,
        String courseId,
        String parentId,
        ItemType type,
        String name,
        String blobPath,
        String contentType,
        Long sizeBytes,
        FileStatus status,
        String createdBy,
        Instant createdAt

) {

    public enum ItemType { FOLDER, FILE; }
    public enum FileStatus { PENDING, READY; }

}
