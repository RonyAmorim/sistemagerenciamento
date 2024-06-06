package br.com.sistemagerenciamento.dto.project;

import br.com.sistemagerenciamento.dto.team.TeamProjectResponseDTO;
import br.com.sistemagerenciamento.dto.user.UserResponseDTO;

public record ProjectResponseDTO(
        Long projectId,
        String name,
        String description,
        String status,
        UserResponseDTO responsibleId,
        TeamProjectResponseDTO teamId
) { }
