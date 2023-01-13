package com.joan.springcloud.ms.courses.mscourses.service;

import com.joan.springcloud.ms.courses.mscourses.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> list();

    Optional<Course> findById(Long id);

    Course save(Course course);

    void delete(Long id);

}
