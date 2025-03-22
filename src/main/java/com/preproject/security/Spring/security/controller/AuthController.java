package com.preproject.security.Spring.security.controller;

import com.preproject.security.Spring.security.model.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.preproject.security.Spring.security.service.UserServiceImpl;

import java.security.Principal;

@Controller
public class AuthController {
    private final UserServiceImpl userService;

    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/success-login")
    public String handleSuccessLogin(Principal principal) {
        User user = userService.findByEmail(principal.getName());

        if (user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN"))) {
            return "redirect:/admin";
        }
        return "redirect:/user/info";
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }
}