package com.joan.springcloud.ms.courses.mscourses.repository;

import com.joan.springcloud.ms.courses.mscourses.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
