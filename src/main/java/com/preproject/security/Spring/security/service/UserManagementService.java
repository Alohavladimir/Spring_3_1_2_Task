package com.preproject.security.Spring.security.service;

import com.preproject.security.Spring.security.model.Role;
import com.preproject.security.Spring.security.model.User;
import com.preproject.security.Spring.security.repository.RoleRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.util.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserManagementService {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserManagementService(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //устанавливает роли пользователю
    //если нет роли при создании пользователя -> назначается ROLE_USER
    public void createUserWithRoles(User user, List<Long> roleIds) {
        setUserRoles(user, roleIds);
        if (user.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Role ROLE_USER not found"));
            user.getRoles().add(defaultRole);
        }
        userService.saveUser(user);
    }

    //обновляет данные пользователя, включая роли и пароль
    //проверяет, не пытается ли пользователь редактировать самого себя
    public void updateUserWithRoles(Long userId, User updatedUser, List<Long> roleIds, String newPassword, String currentUserEmail) {
        User existingUser = userService.getUserById(userId);
        if (existingUser.getEmail().equals(currentUserEmail)) {
            throw new AccessDeniedException("You can't edit your own account");
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(newPassword));
        }
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setAge(updatedUser.getAge());
        setUserRoles(existingUser, roleIds);

        userService.saveUser(existingUser);
    }


    //связывает пользователя с его ролями при создании
    private void setUserRoles(User user, List<Long> roleIds) {
        Set<Role> roles = new HashSet<>();

        // Если есть ID ролей, преобразуем их в объекты Role
        if (roleIds != null && !roleIds.isEmpty()) {
            for (Long roleId : roleIds) {
                Role role = roleRepository.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Role not found with ID: " + roleId));
                roles.add(role);
            }
        }
        user.setRoles(roles);
    }
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}

