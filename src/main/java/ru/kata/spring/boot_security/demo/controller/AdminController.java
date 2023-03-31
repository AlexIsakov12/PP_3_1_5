package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService,RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String index(Model model, Principal principal) {
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleService.findAll());
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "admin";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user, @RequestParam("roles") Set<Role> roles) {
        userService.save(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String getUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.findAll());
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("user") User user, @RequestParam(value = "roles") Set<Role> roles) {
        userService.update(id, user, roles);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
