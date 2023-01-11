package org.joan.springcloud.ms.users.repository;

import org.joan.springcloud.ms.users.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
