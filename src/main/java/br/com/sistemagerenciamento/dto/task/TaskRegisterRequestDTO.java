package br.com.sistemagerenciamento.dto.task;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.domain.Team;
import br.com.sistemagerenciamento.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TaskRegisterRequestDTO(
    @NotBlank String name,
    @NotBlank String description,
    @NotNull LocalDate deadline,
    @NotBlank String status,
    @NotNull LocalDate startDate,
    @NotNull LocalDate endDate,
    @NotNull Project project,
    @NotNull User assignedTo,
    Team team
) { }
