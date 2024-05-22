package br.com.sistemagerenciamento.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "User")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // Usar EnumType.STRING para armazenar o valor como string no banco
    @Column(nullable = false)
    private UserType type; // Use o enum UserType

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @PrePersist
    void prePersist() {
        creationDate = LocalDateTime.now();
    }
}

