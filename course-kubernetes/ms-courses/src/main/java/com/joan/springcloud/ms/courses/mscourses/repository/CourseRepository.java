package com.joan.springcloud.ms.courses.mscourses.repository;

import com.joan.springcloud.ms.courses.mscourses.model.entity.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Modifying
    @Query("delete from CourseUser cu where cu.user_id = ?1")
    public void deleteCourseUserById(Long userId);

}
