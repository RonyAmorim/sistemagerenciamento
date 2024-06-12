package br.com.sistemagerenciamento.dto.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UpdateTaskDTO(
        Long taskId,
        String status,
        LocalDate deadline,
        LocalDate endDate,
        LocalDateTime lastUpdate,
        Long assignedToId
) { }
