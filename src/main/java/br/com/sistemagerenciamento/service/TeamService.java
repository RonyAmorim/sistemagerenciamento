package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.domain.Team;
import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.dto.team.TeamRegisterRequestDTO;
import br.com.sistemagerenciamento.exception.ResourceNotFoundException;
import br.com.sistemagerenciamento.repository.TeamRepository;
import br.com.sistemagerenciamento.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTeamService userTeamService;

    /**
     * Método que cria uma nova equipe
     * @param teamDTO DTO com os dados da equipe
     * @return Equipe criada
     */
    public Team createTeam(TeamRegisterRequestDTO teamDTO) {
        User manager = userRepository.findById(teamDTO.managerId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + teamDTO.managerId()));

        // 2. Cria um objeto Team e preenche os dados
        Team team = new Team();
        team.setName(teamDTO.name());
        team.setManagerId(manager);

        Team createdTeam = teamRepository.save(team);
        userTeamService.addUserToTeam(manager.getUserId(), createdTeam.getTeamId());

        // 3. Salva a equipe no banco
        return createdTeam;
    }

    /**
     * Método que retorna uma equipe pelo ID
     * @param teamId ID da equipe
     * @return Equipe
     */
    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipe não encontrada com ID: " + teamId));
    }

    /**
     * Método que retorna todas as equipes
     * @return Lista de equipes
     */
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    /**
     * Método que atualiza uma equipe
     * @param teamId ID da equipe
     * @param updatedTeam Dados atualizados da equipe
     * @return Equipe atualizada
     */
    public Team updateTeam(Long teamId, Team updatedTeam) {
        Team existingTeam = getTeamById(teamId);
        existingTeam.setProject(updatedTeam.getProject());
        return teamRepository.save(existingTeam);
    }

    /**
     * Método que deleta uma equipe
     * @param teamId ID da equipe
     */
    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }

    /**
     * Método que retorna uma equipe pelo nome
     * @param name Nome da equipe
     * @return Equipe
     */
    public List<Team> findByNameIgnoreCase(String name){
        return teamRepository.findByNameIgnoreCase(name);
    }

    /**
     * Método que retorna equipes de um projeto específico
     * @param projectId ID do projeto
     * @return Lista de equipes
     */
    public List<Team> findByProjectProjectId(Long projectId) {
        return teamRepository.findByProjectProjectId(projectId);
    }

    /**
     * Método que verifica se existe uma equipe com o nome fornecido (ignorando maiúsculas/minúsculas) dentro de um projeto
     * @param  teamId ID da equipe
     * @param project ID do projeto
     * @return True se existir, false se não existir
     */
    public void updateTeamProjectId(Long teamId, Project project) {
        if (teamRepository.existsByTeamId(teamId)) {
            teamRepository.updateTeamProjectId(teamId, project);
        } else {
            throw new ResourceNotFoundException("Equipe não encontrada com o ID: " + teamId);
        }
    }
}
