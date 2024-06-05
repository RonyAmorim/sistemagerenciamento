package br.com.sistemagerenciamento.dto.task;

public record UpdateTaskDTO(
        Long taskId,
        String name,
        String description,
        String status,
        String deadline,
        String startDate,
        String endDate,
        Long projectId,
        Long assignedToId,
        Long teamId
) {
}
