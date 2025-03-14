package com.preproject.security.Spring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm(
            @RequestParam(name = "error", required = false) String error,
            @RequestParam(name = "logout", required = false) String logout,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("error", "Invalid email or password!");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully!");
        }
        return "login";
    }
}