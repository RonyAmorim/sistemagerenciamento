package br.com.sistemagerenciamento.dto.impediment;

import br.com.sistemagerenciamento.dto.project.ProjectTaskResponseDTO;
import br.com.sistemagerenciamento.dto.task.TaskResponseDTO;
import br.com.sistemagerenciamento.dto.user.UserResponseDTO;

public record ImpedimentDTO(
        Long impedimentId,
        TaskResponseDTO taskId,
        String description,
        UserResponseDTO reportedById
) {}
