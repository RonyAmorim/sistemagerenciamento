package br.com.sistemagerenciamento.repository;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.domain.Task;
import br.com.sistemagerenciamento.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Find tasks by project
    List<Task> findByProject(Project project);

    // Find tasks assigned to a specific user
    List<Task> findByAssignedTo(User user);

    // Find tasks with a specific status
    List<Task> findByStatus(String status);

    // Find tasks by deadline
    List<Task> findByDeadline(LocalDate deadline);

    // Find tasks between a start and end date
    List<Task> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    // Find tasks created after a specific date
    List<Task> findByCreationDateAfter(LocalDate creationDate);

    // Find tasks by project and status
    List<Task> findByProjectAndStatus(Project project, String status);

    //Atualizar o status de uma tarefa

}
