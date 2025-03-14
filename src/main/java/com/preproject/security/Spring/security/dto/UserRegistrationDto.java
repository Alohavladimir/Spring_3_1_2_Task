package com.preproject.security.Spring.security.dto;

import jakarta.validation.constraints.*;

public class UserRegistrationDto {
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
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