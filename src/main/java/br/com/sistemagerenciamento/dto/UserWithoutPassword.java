package br.com.sistemagerenciamento.dto;

public record UserWithoutPassword(Long id, String username, String email, String type) {
}
