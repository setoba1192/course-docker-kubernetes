package org.joan.springcloud.ms.users.service;

import org.joan.springcloud.ms.users.model.entity.User;
import org.joan.springcloud.ms.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> list() {
        return (List<User>) this.userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Transactional
    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmailQ(email);
    }
}
