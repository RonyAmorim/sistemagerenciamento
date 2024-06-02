package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.domain.Task;
import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.dto.task.TaskRegisterRequestDTO;
import br.com.sistemagerenciamento.dto.task.TaskResponseDTO;
import br.com.sistemagerenciamento.dto.task.UpdateTaskDTO;
import br.com.sistemagerenciamento.dto.user.UserWithoutPassword;
import br.com.sistemagerenciamento.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmailService emailService;

    // Método para criar uma nova tarefa
    public TaskResponseDTO createTask(TaskRegisterRequestDTO taskDto) {
        Task newTask = new Task();
        newTask.setName(taskDto.name());
        newTask.setDescription(taskDto.description());
        newTask.setDeadline(taskDto.deadline());
        newTask.setStatus(taskDto.status());
        newTask.setCreationDate(LocalDate.now());
        newTask.setStartDate(taskDto.startDate());
        newTask.setEndDate(taskDto.endDate());
        newTask.setProject(taskDto.project());
        newTask.setAssignedTo(taskDto.assignedTo());
        newTask.setTeam(taskDto.team());

        Task savedTask = taskRepository.save(newTask);

        UserWithoutPassword user = userService.findById(newTask.getAssignedTo().getUserId());

        Project project = projectService.findById(savedTask.getProject().getProjectId());

        String to = user.email();
        String subject = "Nova Tarefa Atribuída";
        String templateName = "novaTarefa.html";

        // Enviar email de notificação (criação de tarefa)
        Context context = new Context();
        context.setVariable("assunto", "Nova Tarefa Atribuída");
        context.setVariable("nomeUsuario", user.username());
        context.setVariable("nomeProjeto", project.getName());
        context.setVariable("nomeTarefa", savedTask.getName());
        context.setVariable("descricaoTarefa", savedTask.getDescription());
        context.setVariable("prazoTarefa", savedTask.getDeadline());
        context.setVariable("statusTarefa", savedTask.getStatus());

        emailService.sendEmail(to, subject, templateName, context);

        return convertToTaskResponseDTO(savedTask);
    }

    // Método para listar todas as tarefas (com paginação e filtros opcionais)
    public List<TaskResponseDTO> listTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToTaskResponseDTO)
                .collect(Collectors.toList());
    }

    // Método para buscar uma tarefa por ID
    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + id));
        return convertToTaskResponseDTO(task);
    }

    // Método para atualizar uma tarefa existente
    public TaskResponseDTO updateTask(Long id, UpdateTaskDTO taskDto) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + id));

        // Atualize os campos da tarefa com base no DTO
        existingTask.setName(taskDto.name());
        existingTask.setDescription(taskDto.description());
        existingTask.setDeadline(taskDto.deadline());
        existingTask.setStatus(taskDto.status());
        existingTask.setEndDate(taskDto.endDate());
        existingTask.setAssignedTo(taskDto.assignedTo());

        Task updatedTask = taskRepository.save(existingTask);

        UserWithoutPassword user = userService.findById(updatedTask.getAssignedTo().getUserId());
        Project project = projectService.findById(updatedTask.getProject().getProjectId());

        String to = user.email();
        String subject;
        String templateName;

        // Verificar o status da tarefa e definir o template e assunto do email
        if ("Concluído".equalsIgnoreCase(updatedTask.getStatus())) {
            subject = "Tarefa Concluída";
            templateName = "tarefaConcluida.html";

            Context context = new Context();

            context.setVariable("nomeUsuario", user.username());
            context.setVariable("nomeTarefa", updatedTask.getName());
            context.setVariable("nomeProjeto", project.getName());

            emailService.sendEmail(to, subject, templateName, context);
        } else {
            subject = "Tarefa Atualizada";
            templateName = "tarefaAtualizada.html";

            // Enviar email de notificação
            Context context = new Context();
            context.setVariable("assunto", subject);
            context.setVariable("nomeUsuario", user.username());
            context.setVariable("nomeTarefa", updatedTask.getName());
            context.setVariable("nomeProjeto", project.getName());
            context.setVariable("descricaoTarefa", updatedTask.getDescription());
            context.setVariable("prazoTarefa", updatedTask.getDeadline());
            context.setVariable("statusTarefa", updatedTask.getStatus());

            emailService.sendEmail(to, subject, templateName, context);
        }
        return convertToTaskResponseDTO(updatedTask);
    }


    // Método para excluir uma tarefa
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Tarefa não encontrada com o ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    // Método para buscar tarefas por status
    public List<TaskResponseDTO> findByStatus(String status) {
        return taskRepository.findByStatus(status).stream()
                .map(this::convertToTaskResponseDTO)
                .collect(Collectors.toList());
    }

    // Método para buscar tarefas atribuídas a um usuário
    public List<TaskResponseDTO> findByAssignedToId(Long userId) {
        return taskRepository.findByAssignedTo_UserId(userId).stream()
                .map(this::convertToTaskResponseDTO)
                .collect(Collectors.toList());
    }

    // Método para buscar tarefas de um projeto específico
    public List<TaskResponseDTO> findByProjectId(Long projectId) {
        return taskRepository.findByProject_ProjectId(projectId).stream()
                .map(this::convertToTaskResponseDTO)
                .collect(Collectors.toList());
    }

    // Método para buscar tarefas de um time específico
    public List<TaskResponseDTO> findByTeamId(Long teamId) {
        return taskRepository.findByTeam_TeamId(teamId).stream()
                .map(this::convertToTaskResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDTO> findByCreationDateBetween(LocalDate startDate, LocalDate endDate) {
        return taskRepository.findByCreationDateBetween(startDate, endDate).stream()
                .map(this::convertToTaskResponseDTO)
                .collect(Collectors.toList());
    }

    // Método para buscar tarefas com prazo até uma data específica
    public List<TaskResponseDTO> findByDeadlineLessThanEqual(LocalDate deadline) {
        return taskRepository.findByDeadlineLessThanEqual(deadline).stream()
                .map(this::convertToTaskResponseDTO)
                .collect(Collectors.toList());
    }

    // Método de conversão de Task para TaskResponseDTO
    private TaskResponseDTO convertToTaskResponseDTO(Task task) {
        return new TaskResponseDTO(task.getTaskId(), task.getName(), task.getDescription(), task.getCreationDate(), task.getDeadline(), task.getStatus());
    }
}
