package com.preproject.security.Spring.security.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // "ROLE_ADMIN", "ROLE_USER"

    @Override
    public String getAuthority() {
        return name; // Теперь роль сама является Authority
    }
    public Long getId() {
        return id;
    }

    // Конструктор по умолчанию (обязателен для JPA)
    public Role() {
    }

    // Конструктор с параметром name
    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}