package br.com.sistemagerenciamento.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Task")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private LocalDate deadline;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "assignedTo")
    private User assignedTo;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private Integer estimatedTime;
}

