package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.domain.Task;
import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.dto.task.TaskRegisterRequestDTO;
import br.com.sistemagerenciamento.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Metodo para criar uma tarefa
    public Task createTask(TaskRegisterRequestDTO taskDTO){
        //buscar o usuario
        User assigned_to = userService.findById(taskDTO.assignedTo());

        Project project = projectService.getProjectById(taskDTO.project());

        //criar a tarefa
        Task task = new Task();
        task.setName(taskDTO.name());
        task.setDescription(taskDTO.description());
        task.setCreationDate(LocalDate.now());
        task.setDeadline(taskDTO.deadline());
        task.setAssignedTo(assigned_to);
        task.setStatus(taskDTO.status());
        task.setProject(project);
        task.setStartDate(LocalDate.now());

        return taskRepository.save(task);
    }

    //Método para deletar a tarefa
    public void deleteTask (Long id){taskRepository.deleteById(id);}

    //Método para atualizar

    //listar tarefas de um projeto
    public List<Task> getTasksByProject(Project project) {
        return taskRepository.findByProject(project);
    }

    //listar tarefas pelo responsavel
    public List<Task> getTasksAssignedToUser(User user) {
        return taskRepository.findByAssignedTo(user);
    }

    //listar tarefas pelo ststus
    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findByStatus(status);
    }

    //listar tarefas pelo prazo
    public List<Task> getTasksByDeadline(LocalDate deadline) {
        return taskRepository.findByDeadline(deadline);
    }

    //listar tarefas pelo inicio e fim
    public List<Task> getTasksByStartDateRange(LocalDate startDate, LocalDate endDate) {
        return taskRepository.findByStartDateBetween(startDate, endDate);
    }

    //listar tarefas pela data de criacao
    public List<Task> getTasksCreatedAfter(LocalDate creationDate) {
        return taskRepository.findByCreationDateAfter(creationDate);
    }

    //listar tarefas pelo status do projeto
    public List<Task> getTasksByProjectAndStatus(Project project, String status) {
        return taskRepository.findByProjectAndStatus(project, status);
    }
}
