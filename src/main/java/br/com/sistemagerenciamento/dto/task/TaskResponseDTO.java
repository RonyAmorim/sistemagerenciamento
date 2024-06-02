package br.com.sistemagerenciamento.dto.task;

import java.time.LocalDate;

public record TaskResponseDTO(
    Long taskId,
    String name,
    String description,
    LocalDate creationDate,
    LocalDate deadline,
    String status
) { }
