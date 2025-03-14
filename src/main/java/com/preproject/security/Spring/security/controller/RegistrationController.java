package com.preproject.security.Spring.security.controller;


import com.preproject.security.Spring.security.dto.UserRegistrationDto;
import com.preproject.security.Spring.security.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        System.out.println("Password received: " + registrationDto.getPassword());
        userService.registerUser(registrationDto);
        return "redirect:/login";
    }
}