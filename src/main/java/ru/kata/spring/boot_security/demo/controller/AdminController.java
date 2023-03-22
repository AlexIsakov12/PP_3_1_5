package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userServiceImpl;
    private final RoleRepository roleRepository;

    public AdminController(UserServiceImpl userServiceImpl, RoleRepository roleRepository) {
        this.userServiceImpl = userServiceImpl;
        this.roleRepository = roleRepository;
    }

    @GetMapping()
    public String index(Model model, Principal principal) {
        User user = userServiceImpl.findByUsername(principal.getName());
        model.addAttribute("users", userServiceImpl.findAll());
        model.addAttribute("admininfo", user);
        return "admin";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "add";
    }
    @PostMapping()
    public String create(@ModelAttribute("user") User user, @RequestParam("roles") Set<Role> roles) {
        userServiceImpl.save(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/update-form/{id}")
    public String getUserUpdateForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userServiceImpl.getUserById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "update-form";
    }

    @PostMapping("/update-form")
    public String update(@ModelAttribute("user") User user, @RequestParam(value = "roles") Set<Role> roles) {
        userServiceImpl.update(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/delete-user/{id}")
    public String delete(@PathVariable("id") long id) {
        userServiceImpl.delete(id);
        return "redirect:/admin";
    }


}
