package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.dto.task.TaskRegisterRequestDTO;
import br.com.sistemagerenciamento.dto.task.TaskResponseDTO;
import br.com.sistemagerenciamento.dto.task.UpdateTaskDTO;
import br.com.sistemagerenciamento.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid TaskRegisterRequestDTO taskDto) {
        TaskResponseDTO task = taskService.createTask(taskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> listTasks() {
        List<TaskResponseDTO> tasks = taskService.listTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        TaskResponseDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody @Valid UpdateTaskDTO taskDto) {
        TaskResponseDTO task = taskService.updateTask(id, taskDto);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Tarefa excluída com sucesso");
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponseDTO>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(taskService.findByStatus(status));
    }

    @GetMapping("/assigned-to/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> findByAssignedToId(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.findByAssignedToId(userId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskResponseDTO>> findByProjectId(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.findByProjectId(projectId));
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<TaskResponseDTO>> findByTeamId(@PathVariable Long teamId) {
        return ResponseEntity.ok(taskService.findByTeamId(teamId));
    }

    @GetMapping("/creation-date")
    public ResponseEntity<List<TaskResponseDTO>> findByCreationDateBetween(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(taskService.findByCreationDateBetween(startDate, endDate));
    }

    @GetMapping("/deadline")
    public ResponseEntity<List<TaskResponseDTO>> findByDeadlineLessThanEqual(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline) {
        return ResponseEntity.ok(taskService.findByDeadlineLessThanEqual(deadline));
    }
}
