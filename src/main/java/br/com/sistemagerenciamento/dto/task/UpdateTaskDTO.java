package br.com.sistemagerenciamento.dto.task;

import java.time.LocalDate;

public record UpdateTaskDTO(
        Long taskId,
        String status,
        LocalDate deadline,
        LocalDate endDate,
        Long assignedToId
) { }
