package br.com.sistemagerenciamento.repository;

import br.com.sistemagerenciamento.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Buscar tarefas por status
    List<Task> findByStatus(String status);

    // Buscar tarefas atribuídas a um usuário
    List<Task> findByAssignedToId(Long userId);

    // Buscar tarefas de um projeto específico
    List<Task> findByProjectId(Long projectId);

    // Buscar tarefas de um time específico
    List<Task> findByTeamId(Long teamId);

    // Verificar se existe uma tarefa com o ID fornecido
    boolean existsByTaskId(Long taskId);
}