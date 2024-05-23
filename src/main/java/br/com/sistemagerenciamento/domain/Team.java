package br.com.sistemagerenciamento.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Team")
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne // Muitos times podem pertencer a um projeto
    @JoinColumn(name = "project_id")
    private Project project;
}
