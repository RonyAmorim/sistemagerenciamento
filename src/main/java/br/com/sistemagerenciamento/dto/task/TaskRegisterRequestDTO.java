package br.com.sistemagerenciamento.dto.task;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.domain.Team;
import br.com.sistemagerenciamento.dto.user.UserWithoutPassword;

import java.time.LocalDate;

public record TaskRegisterRequestDTO(
        String name,
        String description,
        String status,
        LocalDate deadline,
        LocalDate startDate,
        LocalDate endDate,
        Project project,
        UserWithoutPassword assignedTo,
        Team team
) {
}
