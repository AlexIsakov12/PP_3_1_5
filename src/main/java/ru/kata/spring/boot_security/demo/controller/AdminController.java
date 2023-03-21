package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping()
    public String index(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("users", userService.findAll());
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
    public String create(@ModelAttribute("user") User user, @RequestParam("roles") List<Role> roles) {
        userService.save(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/update-form/{id}")
    public String getUserUpdateForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "update-form";
    }

    @PostMapping("/update-form")
    public String update(@ModelAttribute("user") User user, @RequestParam(value = "roles") List<Role> roles) {
        userService.update(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/delete-user/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }


}
