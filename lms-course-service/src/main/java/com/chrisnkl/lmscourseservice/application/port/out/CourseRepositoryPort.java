package com.chrisnkl.lmscourseservice.application.port.out;

import com.chrisnkl.lmscourseservice.domain.Course;
import com.chrisnkl.lmscourseservice.domain.PageResult;

public interface CourseRepositoryPort {

    PageResult<Course> getCourses(int page, int size);
    boolean existsByCode(String code);
    Course save(Course course);

}
