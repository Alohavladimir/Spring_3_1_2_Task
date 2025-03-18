package com.preproject.security.Spring.security.init;

import com.preproject.security.Spring.security.model.Role;
import com.preproject.security.Spring.security.model.User;
import com.preproject.security.Spring.security.repository.RoleRepository;
import com.preproject.security.Spring.security.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class DataInitializer {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public DataInitializer(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @PostConstruct
    @Transactional
    public void init() {
        // Проверяем наличие роли ADMIN
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new IllegalStateException("ROLE_ADMIN must be created by RoleInitializer!"));

        // Создаем администратора, если его нет
        if (!userRepository.existsByEmail("admin@example.com")) {
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setPassword(encoder.encode("admin"));
            admin.setFullName("Admin");
            admin.setAge(30);
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
        }
    }
}