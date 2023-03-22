package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
    User findByUsername(String username);
    List<User> findAll();
    void save(User user, Set<Role> roles);
    void update(User user, Set<Role> roles);
    void delete(Long id);
    User getUserById(Long id);
}
