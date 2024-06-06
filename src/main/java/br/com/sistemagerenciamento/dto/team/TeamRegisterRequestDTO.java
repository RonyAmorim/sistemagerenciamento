package br.com.sistemagerenciamento.dto.team;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TeamRegisterRequestDTO(
        @NotBlank(message = "Nome da equipe é obrigatorio") String name,
        @NotNull(message = "ID do gerente é obrigatorio") Long managerId
) { }
