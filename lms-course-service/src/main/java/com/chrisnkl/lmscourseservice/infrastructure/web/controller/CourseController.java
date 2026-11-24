package com.chrisnkl.lmscourseservice.infrastructure.web.controller;

import com.chrisnkl.lmscourseservice.infrastructure.web.response.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value =  {"/api/secure/course"})
@PreAuthorize("isAuthenticated()")
public class CourseController {

    @GetMapping
    public ResponseEntity<ApiResponse> getCourses(@RequestParam(required = false, defaultValue = "0") int page,
                                                  @RequestParam(required = false, defaultValue = "20") int size) {
        Pageable pageable = Pageable.ofSize(size);
        return ResponseEntity.ok().build();

    }

}
