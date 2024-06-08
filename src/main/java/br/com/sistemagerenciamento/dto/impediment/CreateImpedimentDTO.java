package br.com.sistemagerenciamento.dto.impediment;

public record CreateImpedimentDTO(
        Long taskId,
        String description,
        Long reportedById
) {}
