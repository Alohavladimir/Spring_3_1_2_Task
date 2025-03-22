package com.preproject.security.Spring.security.service;

import com.preproject.security.Spring.security.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);
    void deleteUser(Long id);
    User findByEmail(String email);
    User getUserById(Long id);
    List<User> getAllUsers();

}
