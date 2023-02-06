package org.joan.springcloud.ms.users.controller;

import jakarta.validation.Valid;
import org.joan.springcloud.ms.users.model.entity.User;
import org.joan.springcloud.ms.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment environment;

    @GetMapping("/crash")
    public void crash() {
        SpringApplication.exit(applicationContext, () -> -1);
    }

    @GetMapping
    public ResponseEntity<?> list() {

        Map<String, Object> map = new HashMap<>();
        map.put("users", userService.list());
        map.put("podIP", environment.getProperty("MY_POD_NAME"));
        map.put("podName", environment.getProperty("MY_POD_IP"));

        return ResponseEntity.ok(map);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return this.userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody User user, BindingResult result) {

        if (result.hasErrors()) {
            return validate(result);
        }

        if (!user.getEmail().isEmpty() && userService.existsByEmail(user.getEmail()))
            return ResponseEntity.badRequest().body(Collections.singletonMap("Message", "User already exist with this email"));

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors())
            return validate(result);

        Optional<User> foundUser = userService.findByEmail(user.getEmail());

        if (foundUser.isPresent() && !user.getEmail().isEmpty() && user.getEmail().equalsIgnoreCase(foundUser.get().getEmail()))
            return ResponseEntity.badRequest().body(Collections.singletonMap("Message", "User already exist with this email"));

        return userService.findById(id).map(usr -> {

            usr.setEmail(user.getEmail());
            usr.setName(user.getName());
            usr.setPassword(user.getPassword());

            User updatedUser = userService.save(usr);
            return ResponseEntity.ok(updatedUser);

        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (userService.findById(id).isPresent()) {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/users-course")
    public ResponseEntity<?> getUserByCourse(@RequestParam List<Long> ids) {

        return ResponseEntity.ok(this.userService.findAllById(ids));
    }

    private ResponseEntity<?> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "Field " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
