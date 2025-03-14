package com.preproject.security.Spring.security.service;

import com.preproject.security.Spring.security.dto.UserRegistrationDto;
import com.preproject.security.Spring.security.model.Role;
import com.preproject.security.Spring.security.model.User;
import com.preproject.security.Spring.security.repository.RoleRepository;
import com.preproject.security.Spring.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

@Transactional
    public void registerUser(UserRegistrationDto registrationDto) {
    System.out.println("Регистрация пользователя: " + registrationDto.getEmail());
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setFullName(registrationDto.getFullName());
        user.setAge(registrationDto.getAge());

        // Назначение роли ROLE_USER по умолчанию
    Role userRole = roleRepository.findByName("ROLE_USER")
            .orElseThrow(() -> {
                System.out.println("Роль ROLE_USER не найдена!");
                return new RuntimeException("Role not found");
            });
    System.out.println("Роль найдена: " + userRole);

        userRepository.save(user);
    System.out.println("Пользователь сохранен: " + user);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    @Transactional
    public void saveUser(User user) {
        // Если пароль не изменен (уже хэширован), не хэшируем повторно
        if (user.getPassword().startsWith("$2a$")) {
            userRepository.save(user);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }
@Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}