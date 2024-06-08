package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.Task;
import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.dto.project.ProjectTaskResponseDTO;
import br.com.sistemagerenciamento.dto.task.TaskRegisterRequestDTO;
import br.com.sistemagerenciamento.dto.task.TaskResponseDTO;
import br.com.sistemagerenciamento.dto.task.UpdateTaskDTO;
import br.com.sistemagerenciamento.dto.user.UserResponseDTO;
import br.com.sistemagerenciamento.repository.TaskRepository;
import br.com.sistemagerenciamento.repository.ProjectRepository;
import br.com.sistemagerenciamento.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    /**
     * Cria uma nova tarefa e envia um e-mail de notificação.
     *
     * @param taskDTO Dados da tarefa a ser criada.
     * @return DTO da tarefa criada.
     */
    public TaskResponseDTO createTask(TaskRegisterRequestDTO taskDTO) {
        Task task = convertToEntity(taskDTO);
        Task savedTask = taskRepository.save(task);

        // Enviar e-mail de nova tarefa
        sendEmailForNewTask(taskDTO, savedTask);

        return convertToDTO(savedTask);
    }

    /**
     * Atualiza uma tarefa existente e envia e-mails de notificação conforme o status da tarefa.
     *
     * @param taskId ID da tarefa a ser atualizada.
     * @param taskDTO Dados atualizados da tarefa.
     * @return DTO da tarefa atualizada.
     */
    public TaskResponseDTO updateTask(Long taskId, UpdateTaskDTO taskDTO) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();
            updateTaskFromDTO(existingTask, taskDTO);
            Task updatedTask = taskRepository.save(existingTask);

            // Verificar o status e enviar e-mail apropriado
            if ("concluído".equalsIgnoreCase(taskDTO.status())) {
                sendEmailForCompletedTask(updatedTask);
            } else {
                sendEmailForUpdatedTask(updatedTask);
            }

            return convertToDTO(updatedTask);
        } else {
            throw new RuntimeException("Tarefa não encontrada");
        }
    }

    /**
     * Obtém todas as tarefas.
     *
     * @return Lista de DTOs de todas as tarefas.
     */
    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Obtém uma tarefa pelo seu ID.
     *
     * @param taskId ID da tarefa.
     * @return Optional contendo o DTO da tarefa, se encontrada.
     */
    public Optional<TaskResponseDTO> getTaskById(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.map(this::convertToDTO);
    }

    /**
     * Exclui uma tarefa pelo seu ID.
     *
     * @param taskId ID da tarefa a ser excluída.
     */
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    /**
     * Obtém tarefas por prazo.
     *
     * @param deadline Prazo das tarefas.
     * @return Lista de DTOs das tarefas com o prazo especificado.
     */
    public List<TaskResponseDTO> getTasksByDeadline(LocalDate deadline) {
        List<Task> tasks = taskRepository.findByDeadline(deadline);
        return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Obtém tarefas por data de criação.
     *
     * @param creationDate Data de criação das tarefas.
     * @return Lista de DTOs das tarefas com a data de criação especificada.
     */
    public List<TaskResponseDTO> getTasksByCreationDate(LocalDate creationDate) {
        List<Task> tasks = taskRepository.findByCreationDate(creationDate);
        return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Obtém tarefas por ID do projeto.
     *
     * @param projectId ID do projeto.
     * @return Lista de DTOs das tarefas pertencentes ao projeto especificado.
     */
    public List<TaskResponseDTO> getTasksByProjectId(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectProjectId(projectId);
        return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Obtém tarefas por nome.
     *
     * @param name Nome ou parte do nome das tarefas.
     * @return Lista de DTOs das tarefas que contêm o nome especificado.
     */
    public List<TaskResponseDTO> getTasksByName(String name) {
        List<Task> tasks = taskRepository.findByNameContainingIgnoreCase(name);
        return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Obtém tarefas por status.
     *
     * @param status Status das tarefas.
     * @return Lista de DTOs das tarefas com o status especificado.
     */
    public List<TaskResponseDTO> getTasksByStatus(String status) {
        List<Task> tasks = taskRepository.findByStatus(status);
        return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Obtém tarefas atribuídas a um usuário específico.
     *
     * @param assignedToId ID do usuário.
     * @return Lista de DTOs das tarefas atribuídas ao usuário especificado.
     */
    public List<TaskResponseDTO> getTasksByAssignedToUserId(Long assignedToId) {
        List<Task> tasks = taskRepository.findByAssignedToUserId(assignedToId);
        return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Atualiza o status de uma tarefa.
     *
     * @param taskId ID da tarefa.
     * @param status Novo status da tarefa.
     */
    public void updateTaskStatus(Long taskId, String status) {
        taskRepository.updateStatusById(taskId, status);
    }

    /**
     * Atualiza a descrição de uma tarefa.
     *
     * @param taskId ID da tarefa.
     * @param description Nova descrição da tarefa.
     */
    public void updateTaskDescription(Long taskId, String description) {
        taskRepository.updateDescriptionById(taskId, description);
    }
    /**
     * Converte um DTO de registro de tarefa para a entidade `Task`.
     *
     * @param taskDTO DTO de registro de tarefa.
     * @return Entidade `Task` correspondente.
     */
    private Task convertToEntity(TaskRegisterRequestDTO taskDTO) {
        Task task = new Task();
        task.setName(taskDTO.name());
        task.setDescription(taskDTO.description());
        task.setStatus(taskDTO.status());
        task.setDeadline(taskDTO.deadline());
        task.setStartDate(taskDTO.startDate());

        Project project = projectRepository.findById(taskDTO.project())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
        task.setProject(project);

        User assignedTo = userRepository.findById(taskDTO.assignedTo())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        task.setAssignedTo(assignedTo);

        task.setCreationDate(LocalDate.now());
        task.setEndDate(LocalDate.now());

        return task;
    }

    /**
     * Atualiza os campos de uma entidade `Task` com base nos dados de um DTO de atualização.
     *
     * @param task Entidade `Task` a ser atualizada.
     * @param taskDTO DTO de atualização da tarefa.
     */
    private void updateTaskFromDTO(Task task, UpdateTaskDTO taskDTO) {
        task.setStatus(taskDTO.status());
        task.setDeadline(taskDTO.deadline());

        if (taskDTO.assignedToId() != null) {
            User assignedTo = userRepository.findById(taskDTO.assignedToId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            task.setAssignedTo(assignedTo);
        }

        task.setEndDate(taskDTO.endDate());
    }

    /**
     * Converte uma entidade `Task` para um DTO de resposta `TaskResponseDTO`.
     *
     * @param task Entidade `Task` a ser convertida.
     * @return DTO de resposta `TaskResponseDTO` correspondente.
     */
    private TaskResponseDTO convertToDTO(Task task) {
        return new TaskResponseDTO(
                task.getTaskId(),
                task.getName(),
                task.getDescription(),
                task.getStatus(),
                task.getDeadline().toString(),
                task.getStartDate().toString(),
                task.getEndDate() != null ? task.getEndDate().toString() : null,
                new ProjectTaskResponseDTO(task.getProject().getProjectId(), task.getProject().getName()),
                new UserResponseDTO(task.getAssignedTo().getUserId(), task.getAssignedTo().getName(), task.getAssignedTo().getEmail())
        );
    }

    /**
     * Envia um e-mail notificando sobre a criação de uma nova tarefa.
     *
     * @param taskDTO DTO de registro da nova tarefa.
     * @param task Entidade `Task` correspondente à nova tarefa.
     */
    private void sendEmailForNewTask(TaskRegisterRequestDTO taskDTO, Task task) {

        User user = userRepository.findById(taskDTO.assignedTo())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Project project = projectRepository.findById(taskDTO.project())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        String to = user.getEmail();
        String subject = "Nova Tarefa Atribuída";
        String templateName = "novaTarefa.html";

        Context context = new Context();
        context.setVariable("nomeUsuario", user.getName());
        context.setVariable("nomeTarefa", taskDTO.name());
        context.setVariable("nomeProjeto", project.getName());
        context.setVariable("descricaoTarefa", taskDTO.description());
        context.setVariable("statusTarefa", taskDTO.status());
        context.setVariable("prazoTarefa", taskDTO.deadline().toString());

        emailService.sendEmail(to, subject, templateName, context);
    }

    /**
     * Envia um e-mail notificando sobre a conclusão de uma tarefa.
     *
     * @param task Entidade `Task` correspondente à tarefa concluída.
     */
    private void sendEmailForCompletedTask(Task task) {

        User user = task.getAssignedTo();
        Project project = task.getProject();

        String to = user.getEmail();
        String subject = "Tarefa Concluída";
        String templateName = "tarefaConcluida.html";

        Context context = new Context();
        context.setVariable("nomeUsuario", user.getName());
        context.setVariable("nomeTarefa", task.getName());
        context.setVariable("nomeProjeto", project.getName());

        emailService.sendEmail(to, subject, templateName, context);
    }

    /**
     * Envia um e-mail notificando sobre a atualização de uma tarefa.
     *
     * @param task Entidade `Task` correspondente à tarefa atualizada.
     */
    private void sendEmailForUpdatedTask(Task task) {

        User user = task.getAssignedTo();
        Project project = task.getProject();

        String to = user.getEmail();
        String subject = "Tarefa Atualizada";
        String templateName = "tarefaAtualizada.html";

        Context context = new Context();
        context.setVariable("nomeUsuario", user.getName());
        context.setVariable("nomeProjeto", project.getName());
        context.setVariable("nomeTarefa", task.getName());
        context.setVariable("descricaoTarefa", task.getDescription());
        context.setVariable("statusTarefa", task.getStatus());
        context.setVariable("prazoTarefa", task.getDeadline().toString());

        emailService.sendEmail(to, subject, templateName, context);
    }
}
