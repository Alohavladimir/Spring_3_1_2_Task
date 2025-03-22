package com.preproject.security.Spring.security.controller;

import com.preproject.security.Spring.security.model.Role;
import com.preproject.security.Spring.security.model.User;
import com.preproject.security.Spring.security.repository.RoleRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.preproject.security.Spring.security.service.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final RoleRepository roleRepository;

    public AdminController(UserServiceImpl userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user, Principal principal) {
        // Запрет редактирования самого себя
        if (user.getEmail().equals(principal.getName())) {
            throw new AccessDeniedException("You can't edit your own account here");
        }
            // При создании нового пользователя добавляем роль USER по умолчанию
            if (user.getId() == null && user.getRoles().isEmpty()) {
                Role userRole = roleRepository.findByName("ROLE_USER")
                        .orElseThrow(() -> new RuntimeException("Role not found"));
                user.getRoles().add(userRole);
            }
            userService.saveUser(user);
            return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleRepository.findAll()); // Добавляем список всех ролей
        return "edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}