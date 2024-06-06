package br.com.sistemagerenciamento.dto.team;

import br.com.sistemagerenciamento.dto.user.UserResponseDTO;

public record TeamWithoutProjectDTO(
        Long teamId,
        String name
) { }