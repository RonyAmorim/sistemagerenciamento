package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.domain.Team;
import br.com.sistemagerenciamento.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams") // Endpoint base para equipes
public class TeamController {

    @Autowired
    private TeamService teamService;

    // Listar times por ID do projeto
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Team>> listTeamsByProjectId(@PathVariable Long projectId) {
        return ResponseEntity.ok(teamService.listTeamsByProjectId(projectId));
    }

    // Criar uma equipe
    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody @Valid Team team) {
        Team newTeam = teamService.create(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTeam);
    }

    // Atualizar uma equipe
    @PutMapping("/{teamId}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long teamId, @RequestBody @Valid Team updatedTeam) {
        Team team = teamService.update(teamId, updatedTeam);
        return ResponseEntity.ok(team);
    }

    // Deletar uma equipe
    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
        teamService.delete(teamId);
        return ResponseEntity.noContent().build();
    }

    // Buscar uma equipe por ID
    @GetMapping("/{teamId}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamService.getTeamById(teamId));
    }
}
