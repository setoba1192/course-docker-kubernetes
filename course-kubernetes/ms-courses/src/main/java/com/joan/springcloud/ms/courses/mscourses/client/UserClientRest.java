package com.joan.springcloud.ms.courses.mscourses.client;

import com.joan.springcloud.ms.courses.mscourses.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-users", url = "localhost:8001")
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

}
