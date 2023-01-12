package org.joan.springcloud.ms.users.controller;

import org.joan.springcloud.ms.users.model.entity.User;
import org.joan.springcloud.ms.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> list() {
        return ResponseEntity.ok(userService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return this.userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id) {
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

}
