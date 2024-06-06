package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.domain.Team;
import br.com.sistemagerenciamento.dto.project.ProjectRegisterRequestDTO;
import br.com.sistemagerenciamento.dto.project.ProjectResponseDTO;
import br.com.sistemagerenciamento.dto.team.TeamProjectResponseDTO;
import br.com.sistemagerenciamento.dto.user.UserResponseDTO;
import br.com.sistemagerenciamento.exception.ResourceNotFoundException;
import br.com.sistemagerenciamento.service.ProjectService;
import br.com.sistemagerenciamento.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TeamService teamService;

    //Metodo para criar um projeto
    @PostMapping
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectRegisterRequestDTO projectDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    result.getFieldErrors().stream()
                            .map(error -> error.getField() + ": " + error.getDefaultMessage())
                            .collect(Collectors.toList())
            );
        }

        try {
            Project createdProject = projectService.createProject(projectDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToProjectResponseDTO(createdProject));
        } catch (ResourceNotFoundException e) { // Exemplo de tratamento de exceção
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Buscar um projeto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(convertToProjectResponseDTO(project));
    }

    // Buscar todos os projetos
    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects.stream()
                .map(this::convertToProjectResponseDTO)
                .collect(Collectors.toList()));
    }

    // Atualizar um projeto
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id, @RequestBody Project updatedProject) {
        Project project = projectService.updateProject(id, updatedProject);
        return ResponseEntity.ok(convertToProjectResponseDTO(project));
    }

    //Metodo para deletar um projeto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {

        Project project = projectService.getProjectById(id);
        Team team = teamService.getTeamById(project.getTeamId().getTeamId());

        teamService.updateTeamProjectId(team.getTeamId(), null);

        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar projetos por nome
    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProjectResponseDTO>> findProjectsByName(@PathVariable String name) {
        List<Project> projects = projectService.findProjectsByName(name);
        return ResponseEntity.ok(projects.stream()
                .map(this::convertToProjectResponseDTO)
                .collect(Collectors.toList()));
    }

    // Buscar projetos por status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProjectResponseDTO>> findProjectsByStatus(@PathVariable String status) {
        List<Project> projects = projectService.findProjectsByStatus(status);
        return ResponseEntity.ok(projects.stream()
                .map(this::convertToProjectResponseDTO)
                .collect(Collectors.toList()));
    }

    // Buscar projetos por ID do responsável
    @GetMapping("/responsible/{responsibleId}")
    public ResponseEntity<List<ProjectResponseDTO>> findProjectsByResponsibleId(@PathVariable Long responsibleId) {
        List<Project> projects = projectService.findProjectsByResponsibleId(responsibleId);
        return ResponseEntity.ok(projects.stream()
                .map(this::convertToProjectResponseDTO)
                .collect(Collectors.toList()));
    }

    //Metodo para atualizar o status de um projeto
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateProjectStatus(@PathVariable Long id, @RequestBody String status) {
        projectService.updateProjectStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    //Metodo para atualizar a descrição de um projeto
    @PatchMapping("/{id}/description")
    public ResponseEntity<Void> updateProjectDescription(@PathVariable Long id, @RequestBody String description) {
        projectService.updateProjectDescription(id, description);
        return ResponseEntity.noContent().build();
    }

    //Metodo para converter um objeto Project em um objeto ProjectResponseDTO
    private ProjectResponseDTO convertToProjectResponseDTO(Project project) {
        return new ProjectResponseDTO(
                project.getProjectId(),
                project.getName(),
                project.getDescription(),
                project.getStatus(),
                new UserResponseDTO(
                        project.getResponsibleId().getUserId(),
                        project.getResponsibleId().getName(),
                        project.getResponsibleId().getEmail()),
                new TeamProjectResponseDTO(
                        project.getTeamId().getTeamId(),
                        project.getTeamId().getName(),
                        new UserResponseDTO(
                                project.getTeamId().getManagerId().getUserId(),
                                project.getTeamId().getManagerId().getName(),
                                project.getTeamId().getManagerId().getEmail()
                        )
                )
        );
    }

}
