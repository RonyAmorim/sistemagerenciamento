package br.com.sistemagerenciamento.dto.team;

import br.com.sistemagerenciamento.dto.user.UserResponseDTO;

public record TeamResponseDTO(
        Long teamId,
        String name,
        UserResponseDTO managerId,
        Long projectId
) { }
