package com.joan.springcloud.ms.courses.mscourses.controller;

import com.joan.springcloud.ms.courses.mscourses.entity.Course;
import com.joan.springcloud.ms.courses.mscourses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> save(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.courseService.save(course));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Course course, @PathVariable Long id) {
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

}
