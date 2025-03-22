package com.preproject.security.Spring.security.controller;

import com.preproject.security.Spring.security.model.Role;
import com.preproject.security.Spring.security.model.User;
import com.preproject.security.Spring.security.service.UserManagementService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final String REDIRECT_ADMIN = "redirect:/admin";
    private final UserManagementService userManagementService;

    public AdminController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @ModelAttribute("allRoles")
    public List<Role> populateRoles() {
        return userManagementService.getAllRoles();
    }

    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("users", userManagementService.getAllUsers());
        return "admin";
    }

    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute User user, @RequestParam(value = "roleIds", required = false)
                List < Long > roleIds) {
        userManagementService.createUserWithRoles(user, roleIds);
        return REDIRECT_ADMIN;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userManagementService.getUserById(id));
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(
            @PathVariable Long id,
            @ModelAttribute User user,
            @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            Principal principal) {
        userManagementService.updateUserWithRoles(id, user, roleIds, newPassword, principal.getName());
        return REDIRECT_ADMIN;
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userManagementService.deleteUser(id);
        return REDIRECT_ADMIN;
    }
}