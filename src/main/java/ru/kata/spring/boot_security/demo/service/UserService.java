package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);

    User findByUsername(String username);

    List<User> findAll();

    void save(User user);

    void update(User user);

    void delete(Long id);

    User getUserById(Long id);

}
