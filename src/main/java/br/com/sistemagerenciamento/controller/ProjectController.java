package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.dto.project.ProjectRegisterRequestDTO;
import br.com.sistemagerenciamento.dto.project.ProjectResponseDTO;
import br.com.sistemagerenciamento.dto.project.UpdateProjectDTO;
import br.com.sistemagerenciamento.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody @Valid ProjectRegisterRequestDTO projectDto) {
        Project novoProjeto = projectService.create(projectDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProjectResponseDTO(novoProjeto));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> listProjects() {
        List<Project> projects = projectService.listProjects();
        List<ProjectResponseDTO> projectResponseDTOS = projects.stream()
                .map(ProjectResponseDTO::new).
                collect(Collectors.toList());
        return ResponseEntity.ok(projectResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> findById(@PathVariable Long id) {
        Project project = projectService.findById(id);
        return ResponseEntity.ok(new ProjectResponseDTO(project));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id, @RequestBody @Valid UpdateProjectDTO projectDto) {
        Project project = projectService.update(id, projectDto);
        return ResponseEntity.ok(new ProjectResponseDTO(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProjectResponseDTO>> findByStatus(@PathVariable String status) {
        List<Project> projects = projectService.findByStatus(status);
        List<ProjectResponseDTO> projectResponseDTOs = projects.stream()
                .map(ProjectResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(projectResponseDTOs);

    }
}


