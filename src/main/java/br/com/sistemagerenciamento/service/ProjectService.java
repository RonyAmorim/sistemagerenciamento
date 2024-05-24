package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.dto.ProjectRegisterRequestDTO;
import br.com.sistemagerenciamento.dto.UpdateProjectDTO;
import br.com.sistemagerenciamento.repository.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // Lista todos os projetos
    public List<Project> listProjects() {
        return projectRepository.findAll();
    }

    // Busca um projeto por ID
    public Project findById(Long id){
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado com o ID: " + id));
    }

    // Cria um novo projeto
    public Project create(@Valid ProjectRegisterRequestDTO projectDto) { // Recebe o DTO como parâmetro
        if(projectRepository.existsByNameIgnoreCase(projectDto.name())) {
            throw new RuntimeException("Já existe um projeto com o nome: " + projectDto.name());
        }

        Project project = new Project(); // Cria um novo objeto Project
        project.setName(projectDto.name());
        project.setDescription(projectDto.description());
        project.setStatus(projectDto.status());
        project.setCreationDate(LocalDate.now()); // Define a data de criação

        return projectRepository.save(project); // Salva o objeto Project no banco
    }

    // Atualiza um projeto existente
    public Project update(Long id, @Valid UpdateProjectDTO updatedProject) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado com o ID: " + id));

        existingProject.setDescription(updatedProject.description());
        existingProject.setStatus(updatedProject.status());

        return projectRepository.save(existingProject);
    }

    //
    public void delete(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Projeto não encontrado com o ID: " + id);
        }
        projectRepository.deleteById(id);
    }

    // Busca projetos por status
    public List<Project> findByStatus(String status) {
        return projectRepository.findByStatus(status);
    }

    // Verifica se um projeto existe pelo ID
    public boolean existsById(Long id) {
        return projectRepository.existsById(id);
    }
}
