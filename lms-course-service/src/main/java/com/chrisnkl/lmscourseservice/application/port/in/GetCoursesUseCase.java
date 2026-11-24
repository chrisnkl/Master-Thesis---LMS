package com.chrisnkl.lmscourseservice.application.port.in;

import com.chrisnkl.lmscourseservice.domain.Course;
import com.chrisnkl.lmscourseservice.domain.PageResult;
import com.chrisnkl.lmscourseservice.domain.exception.course.NotFoundCourseException;

public interface GetCoursesUseCase {

    PageResult<Course> getCourses(int page, int size) throws NotFoundCourseException;

}
