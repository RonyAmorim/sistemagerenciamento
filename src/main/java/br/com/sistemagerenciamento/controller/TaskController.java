package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.dto.task.TaskRegisterRequestDTO;
import br.com.sistemagerenciamento.dto.task.TaskResponseDTO;
import br.com.sistemagerenciamento.dto.task.UpdateTaskDTO;
import br.com.sistemagerenciamento.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/createtask")
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRegisterRequestDTO taskDTO) {
        TaskResponseDTO createdTask = taskService.createTask(taskDTO);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable Long id, @RequestBody UpdateTaskDTO updateTaskDTO) {
        if (id == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }
        taskService.updateTask(id, updateTaskDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        Optional<TaskResponseDTO> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/deadline/{deadline}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByDeadline(@PathVariable LocalDate deadline) {
        List<TaskResponseDTO> tasks = taskService.getTasksByDeadline(deadline);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/creationDate/{creationDate}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByCreationDate(@PathVariable LocalDate creationDate) {
        List<TaskResponseDTO> tasks = taskService.getTasksByCreationDate(creationDate);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByProjectId(@PathVariable Long projectId) {
        List<TaskResponseDTO> tasks = taskService.getTasksByProjectId(projectId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByName(@PathVariable String name) {
        List<TaskResponseDTO> tasks = taskService.getTasksByName(name);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByStatus(@PathVariable String status) {
        List<TaskResponseDTO> tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/assignedTo/{assignedToId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByAssignedToUserId(@PathVariable Long assignedToId) {
        List<TaskResponseDTO> tasks = taskService.getTasksByAssignedToUserId(assignedToId);
        return ResponseEntity.ok(tasks);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateTaskStatus(@PathVariable Long id, @RequestParam String status) {
        taskService.updateTaskStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/description")
    public ResponseEntity<Void> updateTaskDescription(@PathVariable Long id, @RequestParam String description) {
        taskService.updateTaskDescription(id, description);
        return ResponseEntity.noContent().build();
    }
}
