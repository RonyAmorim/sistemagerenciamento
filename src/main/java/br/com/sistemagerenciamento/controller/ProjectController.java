package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.domain.Project;
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
    public ResponseEntity<Project> criarProjeto(@RequestBody @Valid Project project) {
        Project novoProjeto = projectService.create(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProjeto);
    }

    @GetMapping
    public ResponseEntity<List<Project>> listProjects() {
        return ResponseEntity.ok(projectService.listProjects());
    }

    @GetMapping("/{id}")
        public ResponseEntity<Project> findById(@PathVariable Long id){
            return ResponseEntity.ok(projectService.findById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Project>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(projectService.findByStatus(status));
    }
}
