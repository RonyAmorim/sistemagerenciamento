package br.com.sistemagerenciamento.dto.user;

public record UserWithoutPassword(Long id, String username, String email, String type) {
}
