package org.joan.springcloud.ms.users.service;

import org.joan.springcloud.ms.users.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> list();

    Optional<User> findById(Long id);

    User save(User user);

    void delete(Long id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
