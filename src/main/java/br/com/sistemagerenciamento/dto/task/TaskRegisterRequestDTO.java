package br.com.sistemagerenciamento.dto.task;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.domain.Team;
import br.com.sistemagerenciamento.dto.user.UserResponseDTO;
import br.com.sistemagerenciamento.dto.user.UserWithoutPassword;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskRegisterRequestDTO(
        String name,
        String description,
        String status,
        LocalDate deadline,
        LocalDateTime lastUpdate,
        Long project,
        Long assignedTo
) { }
