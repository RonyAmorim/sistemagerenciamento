package br.com.sistemagerenciamento.dto.task;

import br.com.sistemagerenciamento.domain.Team;
import br.com.sistemagerenciamento.dto.project.ProjectResponseDTO;
import br.com.sistemagerenciamento.dto.user.UserWithoutPassword;

public record TaskResponseDTO(
        Long taskId,
        String name,
        String description,
        String status,
        String deadline,
        String startDate,
        String endDate,
        ProjectResponseDTO project,
        UserWithoutPassword assignedTo,
        Team team
) {
}
