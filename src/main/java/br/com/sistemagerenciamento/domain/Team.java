package br.com.sistemagerenciamento.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Team")
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @Column(nullable = false)
    private String name;

    @ManyToOne // Muitos times podem pertencer a um projeto
    @JoinColumn(name = "projectId")
    private Project project;
}
