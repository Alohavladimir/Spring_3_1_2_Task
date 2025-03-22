package com.preproject.security.Spring.security.controller;

import com.preproject.security.Spring.security.model.Role;
import com.preproject.security.Spring.security.model.User;
import com.preproject.security.Spring.security.repository.RoleRepository;
import com.preproject.security.Spring.security.service.UserManagementService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.preproject.security.Spring.security.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserManagementService userManagementService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserManagementService userManagementService, PasswordEncoder passwordEncoder) {
        this.userManagementService = userManagementService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("users", userManagementService.getAllUsers());
        return "admin";
    }

    // Создание нового пользователя
    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", userManagementService.getAllRoles());
        return "new";
    }
    @PostMapping("/new")
    public String createUser(
            @ModelAttribute User user,
            @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {

        userManagementService.createUserWithRoles(user, roleIds);
        return "redirect:/admin";
    }

    // Редактирование пользователя
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userManagementService.getUserById(id));
        model.addAttribute("allRoles", userManagementService.getAllRoles());
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(
            @PathVariable Long id,
            @ModelAttribute User user,
            @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
            Principal principal, String newPassword) {
        User existingUser = userManagementService.getUserById(id);
        if (newPassword != null && !newPassword.isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(newPassword));
        }


        userManagementService.updateUserWithRoles(id, user, roleIds, principal.getName());
        return "redirect:/admin";
    }

    // Удаление пользователя
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userManagementService.deleteUser(id);
        return "redirect:/admin";
    }
}