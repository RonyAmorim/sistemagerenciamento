package br.com.sistemagerenciamento.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
/**
 * Classe que representa a entidade User
 */
@Entity
@Table(name = "User")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name= "creation_date" ,nullable = false, columnDefinition = "DATE")
    private LocalDate creationDate;

    @PrePersist
    void prePersist() {
        creationDate = LocalDate.now();
    }

}
