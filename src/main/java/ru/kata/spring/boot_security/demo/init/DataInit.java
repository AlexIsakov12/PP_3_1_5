package ru.kata.spring.boot_security.demo.init;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

public class DataInit {
    public static void initUsers(UserService userService, RoleRepository roleRepository) {

        Role userRole = new Role(1L, "ROLE_USER");
        Role adminRole = new Role(2L, "ROLE_ADMIN");

        Set<Role> userSet = new HashSet<>();
        userSet.add(new Role(1L, "ROLE_USER"));

        Set<Role> adminSet = new HashSet<>();
        adminSet.add(new Role(1L, "ROLE_USER"));
        adminSet.add(new Role(2L, "ROLE_ADMIN"));

        User user = new User("user", "Ivan", "Ivanov", "user@mail.ru", "user", userSet);
        User admin = new User("admin", "Admin", "Adminov", "admin@mail.ru", "admin", adminSet);

        roleRepository.save(userRole);
        roleRepository.save(adminRole);

        userService.save(user);
        userService.save(admin);
    }
}
