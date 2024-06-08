package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.Impediment;
import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.domain.Task;
import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.dto.impediment.CreateImpedimentDTO;
import br.com.sistemagerenciamento.dto.impediment.ImpedimentDTO;
import br.com.sistemagerenciamento.dto.project.ProjectTaskResponseDTO;
import br.com.sistemagerenciamento.dto.task.TaskResponseDTO;
import br.com.sistemagerenciamento.dto.user.UserResponseDTO;
import br.com.sistemagerenciamento.repository.ImpedimentRepository;
import br.com.sistemagerenciamento.repository.ProjectRepository;
import br.com.sistemagerenciamento.repository.TaskRepository;
import br.com.sistemagerenciamento.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImpedimentService {

    @Autowired
    private ImpedimentRepository impedimentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    public ImpedimentService(ImpedimentRepository impedimentRepository, TaskRepository taskRepository, UserRepository userRepository, ProjectRepository projectRepository, EmailService emailService) {
        this.impedimentRepository = impedimentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.emailService = emailService;
    }

    public List<ImpedimentDTO> getAllImpediments() {
        return impedimentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ImpedimentDTO getImpedimentById(Long id) {
        Optional<Impediment> impediment = impedimentRepository.findById(id);
        return impediment.map(this::convertToDTO).orElse(null);
    }

    public ImpedimentDTO createImpediment(CreateImpedimentDTO createImpedimentDTO) {
        Task task = taskRepository.findById(createImpedimentDTO.taskId()).orElseThrow();
        User reportedBy = userRepository.findById(createImpedimentDTO.reportedById()).orElseThrow();

        Impediment impediment = new Impediment();
        impediment.setTask(task);
        impediment.setDescription(createImpedimentDTO.description());
        impediment.setReportedBy(reportedBy);

        Impediment savedImpediment = impedimentRepository.save(impediment);

        // Enviar email
        sendEmailForNewImpediment(savedImpediment);

        return convertToDTO(savedImpediment);
    }

    public void deleteImpediment(Long id) {
        impedimentRepository.deleteById(id);
    }

    private ImpedimentDTO convertToDTO(Impediment impediment) {
        Task task = impediment.getTask();
        Project project = task.getProject();
        User assignedTo = task.getAssignedTo();

        TaskResponseDTO taskDTO = new TaskResponseDTO(
                task.getTaskId(),
                task.getName(),
                task.getDescription(),
                task.getStatus(),
                task.getDeadline().toString(),
                task.getStartDate().toString(),
                task.getEndDate().toString(),
                new ProjectTaskResponseDTO(project.getProjectId(), project.getName()),
                new UserResponseDTO(assignedTo.getUserId(), assignedTo.getName(), assignedTo.getEmail())
        );

        UserResponseDTO userDTO = new UserResponseDTO(
                impediment.getReportedBy().getUserId(),
                impediment.getReportedBy().getName(),
                impediment.getReportedBy().getEmail()
        );

        return new ImpedimentDTO(
                impediment.getImpedimentId(),
                taskDTO,
                impediment.getDescription(),
                userDTO
        );
    }

    private void sendEmailForNewImpediment(Impediment impediment) {
        User userSend = impediment.getReportedBy();
        Task task = impediment.getTask();
        Project project = task.getProject();
        Long responsibleId = project.getResponsibleId().getUserId();
        User userResponsible = userRepository.findById(responsibleId).orElseThrow(() -> new RuntimeException("Responsável pelo projeto não encontrado"));

        String to = userResponsible.getEmail();
        String subject = "Novo Impedimento Reportado";
        String templateName = "novoImpedimento";

        Context context = new Context();
        context.setVariable("nomeResponsavel", userResponsible.getName());
        context.setVariable("nomeProjeto", project.getName());
        context.setVariable("descricaoImpedimento", impediment.getDescription());
        context.setVariable("nomeUsuario", userSend.getName());

        emailService.sendEmail(to, subject, templateName, context);
    }
}
