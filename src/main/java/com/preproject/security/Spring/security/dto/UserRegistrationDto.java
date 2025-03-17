package com.preproject.security.Spring.security.dto;

import jakarta.validation.constraints.*;

public class UserRegistrationDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "Password must contain uppercase, lowercase and number")
    private String password;

    @NotBlank(message = "Имя не может быть пустым")
    private String fullName;

    @Min(value = 1, message = "Возраст должен быть положительным числом")
    private int age;

    // Конструкторы
    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String email, String password, String fullName, int age) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.age = age;
    }

    // Геттеры
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    // Сеттеры (кроме пароля)
    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }
}