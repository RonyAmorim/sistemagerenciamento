package br.com.sistemagerenciamento.dto.task;

import br.com.sistemagerenciamento.dto.project.ProjectResponseDTO;
import br.com.sistemagerenciamento.dto.project.ProjectTaskResponseDTO;
import br.com.sistemagerenciamento.dto.user.UserResponseDTO;

public record TaskResponseDTO(
    Long taskId,
    String name,
    String description,
    String status,
    String deadline,
    String startDate,
    String endDate,
    ProjectTaskResponseDTO project,
    UserResponseDTO assignedTo
) { }
