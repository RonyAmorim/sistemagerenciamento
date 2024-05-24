package br.com.sistemagerenciamento.dto;

import br.com.sistemagerenciamento.domain.Project;

public record ProjectResponseDTO(Long projectId, String name, String description, String status) {
    public ProjectResponseDTO(Project project) {
        this(project.getProjectId(), project.getName(), project.getDescription(), project.getStatus());
    }
}
