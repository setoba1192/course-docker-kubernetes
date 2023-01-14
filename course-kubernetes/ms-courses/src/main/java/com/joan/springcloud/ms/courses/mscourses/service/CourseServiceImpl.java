package com.joan.springcloud.ms.courses.mscourses.service;

import com.joan.springcloud.ms.courses.mscourses.client.UserClientRest;
import com.joan.springcloud.ms.courses.mscourses.model.User;
import com.joan.springcloud.ms.courses.mscourses.model.entity.Course;
import com.joan.springcloud.ms.courses.mscourses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    private UserClientRest userClientRest;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, UserClientRest userClientRest) {
        this.courseRepository = courseRepository;
        this.userClientRest = userClientRest;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Course> list() {
        return (List<Course>) this.courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Course> findById(Long id) {
        return this.courseRepository.findById(id);
    }

    @Transactional
    @Override
    public Course save(Course course) {
        return this.courseRepository.save(course);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        this.courseRepository.deleteById(id);
    }

    @Override
    public Optional<User> assignUser(User user, Long courseId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> saveUser(User user, Long courseId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> unAssignUser(User user, Long courseId) {
        return Optional.empty();
    }
}
