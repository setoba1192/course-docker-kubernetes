package com.joan.springcloud.ms.courses.mscourses.service;

import com.joan.springcloud.ms.courses.mscourses.model.User;
import com.joan.springcloud.ms.courses.mscourses.model.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> list();

    Optional<Course> findById(Long id);

    Optional<Course> findByIdWithUsers(Long id, String token);

    Course save(Course course);

    void delete(Long id);

    Optional<User> assignUser(User user, Long courseId, String token);

    Optional<User> saveUser(User user, Long courseId);

    Optional<User> unAssignUser(User user, Long courseId, String token);

    public void deleteCourseUserById(Long userId);


}
