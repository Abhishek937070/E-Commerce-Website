package com.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    // ✅ Add Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {  // <--- this was missing
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {  // <--- this was missing
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {  // <--- this was missing
        this.password = password;
    }
}
