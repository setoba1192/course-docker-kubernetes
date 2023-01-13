package org.joan.springcloud.ms.users.repository;

import org.joan.springcloud.ms.users.model.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
