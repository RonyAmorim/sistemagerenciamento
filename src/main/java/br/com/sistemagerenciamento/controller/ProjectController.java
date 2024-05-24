package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.dto.ProjectRegisterRequestDTO;
import br.com.sistemagerenciamento.dto.UpdateProjectDTO;
import br.com.sistemagerenciamento.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody @Valid ProjectRegisterRequestDTO projectDto) {
        Project novoProjeto = projectService.create(projectDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProjeto);
    }

    @GetMapping
    public ResponseEntity<List<Project>> listProjects() {
        return ResponseEntity.ok(projectService.listProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> findById(@PathVariable Long id) {
        Project project = projectService.findById(id);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody @Valid UpdateProjectDTO projectDto) {
        Project project = projectService.update(id, projectDto);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Project>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(projectService.findByStatus(status));
    }
}


