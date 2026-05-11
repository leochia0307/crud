package com.leoh.crud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "帳號不能為空")
    @Column(unique = true, nullable = false)
    private String username;

    @NotEmpty(message = "密碼不能為空")
    @Size(min = 6, message = "密碼長度至少需要 6 個字元")
    private String password;

    @Column(nullable = false)
    private String role; // 例如: "ADMIN" 或 "USER"

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}