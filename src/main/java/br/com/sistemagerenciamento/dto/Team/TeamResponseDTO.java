package br.com.sistemagerenciamento.dto.Team;

import br.com.sistemagerenciamento.dto.user.UserResponseDTO;

public record TeamResponseDTO(
        Long teamId,
        String name,
        UserResponseDTO managerId,
        Long projectId
) { }
