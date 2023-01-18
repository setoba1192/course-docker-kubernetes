package org.joan.springcloud.ms.users.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ms-courses", url = "host.docker.internal:8002")
public interface CourseFeignClient {

    @DeleteMapping("/delete-course-user/{userId}")
    public void deleteCourseUserByUserId(@PathVariable Long userId);

}
