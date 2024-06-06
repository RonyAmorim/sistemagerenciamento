package br.com.sistemagerenciamento.dto.team;

import br.com.sistemagerenciamento.dto.user.UserResponseDTO;

public record TeamProjectResponseDTO(
        Long teamId,
        String name,
        UserResponseDTO managerId
) { }
