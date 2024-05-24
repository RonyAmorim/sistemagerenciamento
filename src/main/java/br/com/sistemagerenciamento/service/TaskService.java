package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.Task;
import br.com.sistemagerenciamento.repository.TaskRepository;
import org.thymeleaf.context.Context;


public class TaskService {

    TaskRepository taskRepository;

    EmailService emailService;

    public Task createTask(Task task) {
        // ...
        Context context = new Context();
        context.setVariable("nomeUsuario", task.getAssignedTo().getName());
        context.setVariable("nomeProjeto", task.getProject().getName());
        context.setVariable("nomeTarefa", task.getName());
        context.setVariable("descricaoTarefa", task.getDescription());
        context.setVariable("prazoTarefa", task.getDeadline());
        context.setVariable("statusTarefa", task.getStatus());
        emailService.sendEmail(task.getAssignedTo().getEmail(), "Nova Tarefa Criada", "nova_tarefa.html", context);
        return taskRepository.save(task);
    }

}
