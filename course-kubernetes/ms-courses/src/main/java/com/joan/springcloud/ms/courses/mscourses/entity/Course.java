package com.joan.springcloud.ms.courses.mscourses.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CourseUser> courseUsers;

    public void addCourseUser(CourseUser courseUser){
        this.courseUsers.add(courseUser);
    }

    public void removeCourseUser(CourseUser courseUser){
        this.courseUsers.remove(courseUser);
    }
}
