package br.com.sistemagerenciamento.dto.user;

public record UserResponseDTO(
        Long id,
        String username,
        String email
) { }
