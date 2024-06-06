package br.com.sistemagerenciamento.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectRegisterRequestDTO(
        @NotBlank String name,
        String description,
        @NotBlank String status,
        @NotNull Long responsibleId,
        @NotNull Long teamId
) { }
