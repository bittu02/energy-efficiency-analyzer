package com.example.auth.model;

import jakarta.persistence.*;
import lombok.*;
import com.example.auth.enums.Role; // Ensure you have an enum for Role

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore // Prevents password from being exposed in API responses
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // Ensure role is always set
    private Role role;

    // Constructor without 'id' for easier object creation
    public User(String email, String password, Role role) {
        this.email = email;
        this.password = new BCryptPasswordEncoder().encode(password); // Hash password
        this.role = role;
    }

    // Override equals and hashCode for proper entity comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
