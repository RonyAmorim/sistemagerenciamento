package br.com.sistemagerenciamento.repository;

import br.com.sistemagerenciamento.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Buscar tarefas por status
    List<Task> findByStatus(String status);

    // Buscar tarefas atribuídas a um usuário
    List<Task> findByAssignedTo_UserId(Long userId);

    // Buscar tarefas de um projeto específico
    List<Task> findByProject_ProjectId(Long projectId);

    // Buscar tarefas entre duas datas (opcional)
    List<Task> findByCreationDateBetween(LocalDate startDate, LocalDate endDate);

    // Buscar tarefas com prazo até uma data específica (opcional)
    List<Task> findByDeadlineLessThanEqual(LocalDate deadline);

    List<Task> findByTeam_TeamId(Long teamId);

    // Verificar se existe uma tarefa com o ID fornecido
    boolean existsByTaskId(Long taskId);

}