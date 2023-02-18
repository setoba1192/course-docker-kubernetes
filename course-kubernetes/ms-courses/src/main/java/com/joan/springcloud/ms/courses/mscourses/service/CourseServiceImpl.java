package com.joan.springcloud.ms.courses.mscourses.service;

import com.joan.springcloud.ms.courses.mscourses.client.UserClientRest;
import com.joan.springcloud.ms.courses.mscourses.model.User;
import com.joan.springcloud.ms.courses.mscourses.model.entity.Course;
import com.joan.springcloud.ms.courses.mscourses.model.entity.CourseUser;
import com.joan.springcloud.ms.courses.mscourses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    @Override
    public Optional<Course> findByIdWithUsers(Long id, String token) {

        return this.courseRepository.findById(id).map(course -> {
            course.setUsers(this.userClientRest.getUserByCourse(course.getCourseUsers()
                    .stream()
                    .map(courseUser -> courseUser.getUserId()).collect(Collectors.toList()), token));
            return course;
        });
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

    @Transactional
    @Override
    public Optional<User> assignUser(User user, Long courseId, String token) {

        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            User userMs = userClientRest.findById(user.getId(), token);

            Course c = course.get();
            CourseUser courseUser = CourseUser.builder()
                    .userId(userMs.getId())
                    .build();
            c.addCourseUser(courseUser);
            courseRepository.save(c);

            return Optional.of(userMs);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<User> saveUser(User user, Long courseId) {

        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            User userMs = userClientRest.save(user);

            Course c = course.get();
            CourseUser courseUser = CourseUser.builder()
                    .userId(userMs.getId())
                    .build();
            c.addCourseUser(courseUser);
            courseRepository.save(c);

            return Optional.of(userMs);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<User> unAssignUser(User user, Long courseId, String token) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            User userMs = userClientRest.findById(user.getId(), token);

            Course c = course.get();
            CourseUser courseUser = CourseUser.builder()
                    .userId(userMs.getId())
                    .build();
            c.removeCourseUser(courseUser);
            courseRepository.save(c);

            return Optional.of(userMs);
        }

        return Optional.empty();
    }

    @Transactional
    @Override
    public void deleteCourseUserById(Long userId) {
        this.courseRepository.deleteCourseUserById(userId);
    }
}
