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

    /**
     * Método que cria um novo projeto
     * @param projectDTO DTO com os dados do projeto
     * @return Projeto criado
     */
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

    /**
     * Método que retorna um projeto pelo ID
     * @param id ID do projeto
     * @return Projeto
     */
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado com ID: " + id));
    }

    /**
     * Método que retorna todos os projetos
     * @return Lista de projetos
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * Método que atualiza um projeto
     * @param id ID do projeto
     * @param updatedProject Projeto atualizado
     * @return Projeto atualizado
     */
    public Project updateProject(Long id, Project updatedProject) {
        Project existingProject = getProjectById(id);
        existingProject.setDescription(updatedProject.getDescription());
        existingProject.setStatus(updatedProject.getStatus());

        return existingProject;
    }

    /**
     * Método que deleta um projeto
     * @param id ID do projeto
     */
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    /**
     * Método que retorna projetos por nome
     * @param name Nome do projeto
     * @return Lista de projetos
     */
    public List<Project> findProjectsByName(String name) {
        return projectRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Método que retorna projetos por status
     * @param status Status do projeto
     * @return Lista de projetos
     */
    public List<Project> findProjectsByStatus(String status) {
        return projectRepository.findByStatus(status);
    }

    /**
     * Método que retorna projetos por ID do responsável
     * @param responsibleId ID do responsável
     * @return Lista de projetos
     */
    public List<Project> findProjectsByResponsibleId(Long responsibleId) {
        return projectRepository.findByResponsibleIdUserId(responsibleId);
    }

    /**
     * Método que atualiza o status de um projeto
     * @param id ID do projeto
     * @param status Novo status
     */
    public void updateProjectStatus(Long id, String status) {
        projectRepository.updateStatusById(id, status);
    }

    /**
     * Método que atualiza a descrição de um projeto
     * @param id ID do projeto
     * @param description Nova descrição
     */
    public void updateProjectDescription(Long id, String description) {
        projectRepository.updateDescriptionById(id, description);
    }
}
