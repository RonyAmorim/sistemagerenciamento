package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> listProjects() {
        return projectRepository.findAll();
    }

    public Project findById(Long id){
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado com o ID: " + id));
    }
    public Project create(Project project) {
        if(projectRepository.existsByNameIgnoreCase(project.getName())) {
            throw new RuntimeException("Já existe um projeto com o nome: " + project.getName());
        }

        return projectRepository.save(project);
    }

    public Project update(Long id, Project updatedProject) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado com o ID: " + id));

        existingProject.setName(updatedProject.getName());
        existingProject.setDescription(updatedProject.getDescription());
        existingProject.setStatus(updatedProject.getStatus());

        return projectRepository.save(existingProject);
    }

    public void delete(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Projeto não encontrado com o ID: " + id);
        }
        projectRepository.deleteById(id);
    }

    public List<Project> findByStatus(String status) {
        return projectRepository.findByStatus(status);
    }
}
