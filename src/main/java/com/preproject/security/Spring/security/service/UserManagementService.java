package com.preproject.security.Spring.security.service;

import com.preproject.security.Spring.security.model.Role;
import com.preproject.security.Spring.security.model.User;
import com.preproject.security.Spring.security.repository.RoleRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserManagementService {
    private final UserService userService;
    private final RoleRepository roleRepository;

    public UserManagementService(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    // Создание пользователя с ролями
    public void createUserWithRoles(User user, List<Long> roleIds) {
        setUserRoles(user, roleIds);

        // Добавляем роль USER по умолчанию, если роли не выбраны
        if (user.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Role ROLE_USER not found"));
            user.getRoles().add(defaultRole);
        }
        userService.saveUser(user);
    }

    // Обновление пользователя с ролями
    public void updateUserWithRoles(Long userId, User updatedUser, List<Long> roleIds, String currentUserEmail) {
        User existingUser = userService.getUserById(userId);

        // Проверка на редактирование самого себя
        if (existingUser.getEmail().equals(currentUserEmail)) {
            throw new AccessDeniedException("You can't edit your own account");
        }

        setUserRoles(updatedUser, roleIds);
        userService.saveUser(updatedUser);
    }

    // Установка ролей
    private void setUserRoles(User user, List<Long> roleIds) {
        Set<Role> roles = new HashSet<>();
        if (roleIds != null && !roleIds.isEmpty()) {
            roles = roleIds.stream()
                    .map(roleId -> roleRepository.findById(roleId)
                            .orElseThrow(() -> new RuntimeException("Role not found")))
                    .collect(Collectors.toSet());
        }
        user.setRoles(roles);
    }
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Получение всех ролей
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Получение пользователя по ID
    public User getUserById(Long id) {
        return userService.getUserById(id);
    }

    // Удаление пользователя
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}

