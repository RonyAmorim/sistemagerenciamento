package br.com.sistemagerenciamento.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequestDTO(
        @NotBlank(message = "A senha antiga não pode estar em branco")
        String oldPassword,

        @NotBlank(message = "A nova senha não pode estar em branco")
        @Size(min = 8, message = "A nova senha deve ter no mínimo 8 caracteres")
        String newPassword
) {
}