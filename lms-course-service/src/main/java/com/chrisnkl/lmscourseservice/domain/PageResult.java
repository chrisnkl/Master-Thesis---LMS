package com.chrisnkl.lmscourseservice.domain;

import java.util.List;

public record PageResult<T>(

        List<T> items,
        int page,
        int size,
        long totalElements,
        int totalPages

) { }
