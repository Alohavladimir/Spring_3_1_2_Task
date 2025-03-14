package com.preproject.security.Spring.security.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // "ROLE_ADMIN", "ROLE_USER"

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
}