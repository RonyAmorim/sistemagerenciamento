package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.Team;
import br.com.sistemagerenciamento.repository.ProjectRepository;
import br.com.sistemagerenciamento.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public List<Team> listTeamsByProjectId(Long projectId) {
        return (List<Team>) teamRepository.findByProjectProjectId(projectId);
    }

    //criar uma equipe
    public Team create(Team team) {
        if (teamRepository.existsByNameIgnoreCaseAndProjectProjectId(team.getName(), team.getProject().getProjectId())) {
            throw new RuntimeException("Já existe uma equipe com o nome: " + team.getName() + " neste projeto.");
        }
        return teamRepository.save(team);
    }

    //atualizar uma equipe
    public Team update(Long teamId, Team updatedTeam) {
        Team existingTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada com o ID: " + teamId));

        // Verifica se o novo nome já existe em outra equipe do mesmo projeto
        if (!existingTeam.getName().equalsIgnoreCase(updatedTeam.getName()) &&
                teamRepository.existsByNameIgnoreCaseAndProjectProjectId(updatedTeam.getName(), existingTeam.getProject().getProjectId())) {
            throw new RuntimeException("Já existe uam equipe com o nome: " + updatedTeam.getName() + " neste projeto.");
        }

        existingTeam.setName(updatedTeam.getName());

        return teamRepository.save(existingTeam);
    }

    @Transactional
    //deletar uma equipe
    public void delete(Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new RuntimeException("Time não encontrado com o ID: " + teamId);
        }
        teamRepository.deleteById(teamId);
    }

    //encontrar uma equipe por um projeto
    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Time não encontrado com o ID: " + teamId));
    }
}
