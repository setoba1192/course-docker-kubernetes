package com.joan.springcloud.ms.courses.mscourses.service;

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

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
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
}
