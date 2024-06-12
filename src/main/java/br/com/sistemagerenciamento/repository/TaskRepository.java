package br.com.sistemagerenciamento.repository;

import br.com.sistemagerenciamento.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Buscar tarefa por ID (já incluso em JpaRepository)
    List<Task> findByDeadline(LocalDate deadline);

    // Buscar tarefas por data de criação
    List<Task> findByCreationDate(LocalDate creationDate);

    // Buscar tarefas por projeto
    List<Task> findByProjectProjectId(Long projectId);

    // Buscar tarefas por nome (ignorando maiúsculas/minúsculas) - alterado para containing
    List<Task> findByNameContainingIgnoreCase(String name);

    // Buscar tarefas por status
    List<Task> findByStatus(String status);

    // Buscar tarefas pelo ID do responsável
    List<Task> findByAssignedToUserId(Long assignedToId);

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.status = :status WHERE t.taskId = :taskId")
    // Atualizar o status de uma tarefa por ID
    void updateStatusById(Long taskId, String status);

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.description = :description WHERE t.taskId = :taskId")
    // Atualizar a descrição de uma tarefa por ID
    void updateDescriptionById(Long taskId, String description);
}
