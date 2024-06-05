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

    @ManyToOne
    @JoinColumn(name =  "manager_id")
    private User managerId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
