package br.com.sistemagerenciamento.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Impediment")
@Data
public class Impediment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "impediment_id")
    private Long impedimentId;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "realted_to")
    private User reportedBy;
}
