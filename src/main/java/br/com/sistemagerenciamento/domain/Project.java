package br.com.sistemagerenciamento.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
/**
 * Classe que representa a entidade Project
 */
@Entity
@Table(name = "Project")
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @ManyToOne
    @JoinColumn(name = "responsible_id")
    private User responsibleId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team teamId;
}
