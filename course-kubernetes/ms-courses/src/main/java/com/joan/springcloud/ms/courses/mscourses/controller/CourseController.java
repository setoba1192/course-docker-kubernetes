package com.joan.springcloud.ms.courses.mscourses.controller;

import com.joan.springcloud.ms.courses.mscourses.model.User;
import com.joan.springcloud.ms.courses.mscourses.model.entity.Course;
import com.joan.springcloud.ms.courses.mscourses.service.CourseService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/")
@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> list() {
        return this.courseService.list();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return this.courseService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Course course, BindingResult result) {

        if (result.hasErrors())
            return validate(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.courseService.save(course));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors())
            return validate(result);

        return this.courseService.findById(id).map(c -> {
            c.setName(course.getName());
            return ResponseEntity.ok(courseService.save(c));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return this.courseService.findById(id).map(c -> {
            this.courseService.delete(c.getId());
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/assign-user/{courseId}")
    public ResponseEntity<?> assignUser(@RequestBody User user, @PathVariable Long courseId) {

        try {
            return courseService.assignUser(user, courseId).map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "User doesn't exist or failed communication with the server: " + e.getMessage()));
        }
    }

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> saveUser(@RequestBody User user, @PathVariable Long courseId) {
        try {
            return courseService.saveUser(user, courseId).map(u -> ResponseEntity.status(HttpStatus.CREATED).body(u))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "User couldn't be created or failed communication with the server: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete-course-user/{courseId}")
    public ResponseEntity<?> deleteUser(@RequestBody User user, @PathVariable Long courseId) {
        try {
            return courseService.unAssignUser(user, courseId).map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "User couldn't be deleted or failed communication with the server: " + e.getMessage()));
        }
    }

    @GetMapping("/users-course/{courseId}")
    public ResponseEntity<?> findByIdWithUsers(@PathVariable Long courseId) {
        return this.courseService.findByIdWithUsers(courseId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        this.courseService.deleteCourseUserById(userId);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<?> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "Field " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
