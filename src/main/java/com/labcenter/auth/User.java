package com.labcenter.auth;

import java.io.Serializable;
import java.util.List;

import com.labcenter.security.Encrypt;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Entity(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotEmpty
    @Column(unique = true)
    private String username;
    @NotBlank
    @NotEmpty
    @Column(unique = true)
    private String email;
    @NotBlank
    @NotEmpty
    private String password;
    @JsonbTypeAdapter(RolesAdapter.class)
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

   

    public User(String username, String email, String password, List<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User() {
    }

    @PrePersist
    public void init() {
        password = Encrypt.encrypt(password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> role) {
        this.roles = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
