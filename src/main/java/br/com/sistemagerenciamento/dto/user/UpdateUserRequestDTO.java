package br.com.sistemagerenciamento.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequestDTO(
        @NotBlank(message = "O nome não pode estar em branco")
        @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres")
        String name,

        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "O tipo não pode estar em branco")
        String type
) {
}