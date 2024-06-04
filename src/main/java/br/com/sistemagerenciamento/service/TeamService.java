package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.Project;
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

    //listar equipe por ID do projeto
    public List<Team> listTeamsByProjectId(Long projectId) {
        return teamRepository.findByProjectProjectId(projectId);
    }

    //criar uma equipe
    public Team create(Team team) {
        if (teamRepository.existsByNameIgnoreCaseAndProjectProjectId(team.getName(), team.getProject().getProjectId())) {
            throw new RuntimeException("Já existe uma equipe com o nome: " + team.getName() + " neste projeto.");
        }

        Project project = projectRepository.findById(team.getProject().getProjectId())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado com o ID: " + team.getProject().getProjectId())); //verifica se o projeto existe

        //seta o projeto da equipe
        team.setProject(project);


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
        //verifica se a equipe existe
        if (!teamRepository.existsById(teamId)) {
            throw new RuntimeException("Equipe não encontrada com o ID: " + teamId);
        }
        teamRepository.deleteById(teamId); //deleta a equipe
    }

    //encontrar uma equipe por um projeto
    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId) //busca a equipe pelo ID
                .orElseThrow(() -> new RuntimeException("Time não encontrado com o ID: " + teamId));
    }
}
