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

    public Team createTeam(TeamRegisterRequestDTO teamDTO) {
        User manager = userRepository.findById(teamDTO.managerId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + teamDTO.managerId()));

        // 2. Cria um objeto Team e preenche os dados
        Team team = new Team();
        team.setName(teamDTO.name());
        team.setManagerId(manager);

        // 3. Salva a equipe no banco
        return teamRepository.save(team);
    }

    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipe não encontrada com ID: " + teamId));
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team updateTeam(Long teamId, Team updatedTeam) {
        Team existingTeam = getTeamById(teamId);
        existingTeam.setProject(updatedTeam.getProject());
        return teamRepository.save(existingTeam);
    }

    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }

    public List<Team> findByNameIgnoreCase(String name){
        return teamRepository.findByNameIgnoreCase(name);
    }

    public List<Team> findByProjectProjectId(Long projectId) {
        return teamRepository.findByProjectProjectId(projectId);
    }

    public void updateTeamProjectId(Long teamId, Project project) {
        if (teamRepository.existsByTeamId(teamId)) {
            teamRepository.updateTeamProjectId(teamId, project);
        } else {
            throw new ResourceNotFoundException("Equipe não encontrada com o ID: " + teamId);
        }
    }
}
