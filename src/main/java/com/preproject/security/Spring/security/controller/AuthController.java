package com.preproject.security.Spring.security.controller;

import com.preproject.security.Spring.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.preproject.security.Spring.security.service.UserService;

import java.security.Principal;

@Controller // Добавьте аннотацию @Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
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

    @GetMapping("/access-denied")
    public String handleAccessDenied() {
        return "access-denied";
    }
}