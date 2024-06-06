package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.domain.Team;
import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.dto.project.ProjectRegisterRequestDTO;
import br.com.sistemagerenciamento.exception.ResourceNotFoundException;
import br.com.sistemagerenciamento.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

    public Project createProject(ProjectRegisterRequestDTO projectDTO) {
        // 1. Busca o usuário responsável e a equipe pelo ID
        User responsibleUser = userService.findById(projectDTO.responsibleId());

        Team team = teamService.getTeamById(projectDTO.teamId());

        // 2. Cria um novo objeto Project e preenche os dados do DTO
        Project project = new Project();
        project.setName(projectDTO.name());
        project.setDescription(projectDTO.description());
        project.setStatus(projectDTO.status());
        project.setResponsibleId(responsibleUser);
        project.setTeamId(team);
        project.setCreationDate(LocalDate.now());

        // 3. Salva o projeto no banco de dados
        Project createdProject = projectRepository.save(project);

        // 4. Atualiza o ID do projeto na equipe (se necessário)
        teamService.updateTeamProjectId(team.getTeamId(), createdProject);

        return createdProject;
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado com ID: " + id));
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project updateProject(Long id, Project updatedProject) {
        Project existingProject = getProjectById(id);
        existingProject.setDescription(updatedProject.getDescription());
        existingProject.setStatus(updatedProject.getStatus());

        return existingProject;
    }


    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public List<Project> findProjectsByName(String name) {
        return projectRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Project> findProjectsByStatus(String status) {
        return projectRepository.findByStatus(status);
    }

    public List<Project> findProjectsByResponsibleId(Long responsibleId) {
        return projectRepository.findByResponsibleIdUserId(responsibleId);
    }


    public void updateProjectStatus(Long id, String status) {
        projectRepository.updateStatusById(id, status);
    }

    public void updateProjectDescription(Long id, String description) {
        projectRepository.updateDescriptionById(id, description);
    }
}
