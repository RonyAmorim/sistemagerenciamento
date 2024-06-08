package br.com.sistemagerenciamento.dto.impediment;

public record ImpedimentDTO(
        Long impedimentId,
        Long taskId,
        String description,
        Long reportedById
) {}
