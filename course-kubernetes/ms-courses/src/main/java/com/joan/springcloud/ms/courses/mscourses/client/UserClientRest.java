package com.joan.springcloud.ms.courses.mscourses.client;

import com.joan.springcloud.ms.courses.mscourses.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@FeignClient(name = "ms-users", url = "${ms.users.url}")
public interface UserClientRest {

    /**
     * get user by ID
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id);

    @PostMapping("/")
    public User save(@RequestBody User user);

    @GetMapping("/users-course")
    List<User> getUserByCourse(@RequestParam Collection<Long> ids);

}
