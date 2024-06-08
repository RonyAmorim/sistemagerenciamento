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

    List<Task> findByDeadline(LocalDate deadline);

    List<Task> findByCreationDate(LocalDate creationDate);

    List<Task> findByProjectProjectId(Long projectId);

    List<Task> findByNameContainingIgnoreCase(String name);

    List<Task> findByStatus(String status);

    List<Task> findByAssignedToUserId(Long assignedToId);

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.status = :status WHERE t.taskId = :taskId")
    void updateStatusById(Long taskId, String status);

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.description = :description WHERE t.taskId = :taskId")
    void updateDescriptionById(Long taskId, String description);
}
