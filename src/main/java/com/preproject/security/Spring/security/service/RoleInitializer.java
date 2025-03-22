package com.preproject.security.Spring.security.service;

import com.preproject.security.Spring.security.model.Role;
import com.preproject.security.Spring.security.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleInitializer {
    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @PostConstruct
    @Transactional
    public void init() {
        createRoleIfNotExists("ROLE_USER");
        createRoleIfNotExists("ROLE_ADMIN");
    }

    public void createRoleIfNotExists(String name) {
        if (roleRepository.findByName(name).isEmpty()) { // Используем параметр name
            roleRepository.save(new Role(name));
        }
    }
}