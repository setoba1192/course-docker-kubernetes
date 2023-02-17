package org.joan.springcloud.ms.users.service;

import org.joan.springcloud.ms.users.client.CourseFeignClient;
import org.joan.springcloud.ms.users.model.entity.User;
import org.joan.springcloud.ms.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    private CourseFeignClient courseClientRest;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CourseFeignClient courseClientRest) {
        this.userRepository = userRepository;
        this.courseClientRest = courseClientRest;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        this.courseClientRest.deleteCourseUserByUserId(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmailQ(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public List<User> findAllById(Collection<Long> ids) {
        return (List<User>) this.userRepository.findAllById(ids);
    }
}
