package com.chrisnkl.lmscourseservice.application.service;

import com.chrisnkl.lmscourseservice.application.port.in.CreateCoursesUseCase;
import com.chrisnkl.lmscourseservice.application.port.in.GetCoursesUseCase;
import com.chrisnkl.lmscourseservice.application.port.out.CourseRepositoryPort;
import com.chrisnkl.lmscourseservice.domain.Course;
import com.chrisnkl.lmscourseservice.domain.PageResult;
import com.chrisnkl.lmscourseservice.domain.exception.course.CourseCreationException;

public class CourseService implements CreateCoursesUseCase, GetCoursesUseCase {

    private final CourseRepositoryPort courseRepositoryPort;

    public CourseService(CourseRepositoryPort courseRepositoryPort) {
        this.courseRepositoryPort = courseRepositoryPort;
    }

    @Override
    public Course create(CreateCourseCommand command) {

        if (courseRepositoryPort.existsByCode(command.code())) throw new CourseCreationException(String.format("Course with code %s already exists.", command.code()));
        Course course = Course.create(command.code(), command.title(), command.summary(), command.ownedBy());

        return courseRepositoryPort.save(course);

    }

    @Override
    public PageResult<Course> getCourses(int page, int size) {
        return courseRepositoryPort.getCourses(page, size);
    }
}
