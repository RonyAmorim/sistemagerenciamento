package br.com.sistemagerenciamento.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Impediment")
@Data
public class Impediment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long impedimentId;

    @ManyToOne
    @JoinColumn(name = "TarefaID")
    private Task task;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "RelatadoPor")
    private User reportedBy;
}
