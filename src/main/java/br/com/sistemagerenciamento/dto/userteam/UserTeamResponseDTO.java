package br.com.sistemagerenciamento.dto.userteam;

import br.com.sistemagerenciamento.dto.team.TeamWithoutProjectDTO;
import br.com.sistemagerenciamento.dto.user.UserResponseDTO;

public record UserTeamResponseDTO(
        UserResponseDTO userId,
        TeamWithoutProjectDTO teamId
) { }
