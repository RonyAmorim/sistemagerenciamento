package br.com.sistemagerenciamento.dto.task;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.domain.Team;
import br.com.sistemagerenciamento.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateTaskDTO(
     String name,
     String description,
     LocalDate deadline,
     String status,
     LocalDate endDate,
     User assignedTo
) { }
