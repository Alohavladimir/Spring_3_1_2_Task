package com.preproject.security.Spring.security.repository;

import com.preproject.security.Spring.security.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //запрос нужен для LAZY загрузки данных
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email")
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    //загружаем роли автоматически
    @EntityGraph(attributePaths = {"roles"})
    @Override
    List<User> findAll();
}


