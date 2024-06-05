package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.domain.Team;
import br.com.sistemagerenciamento.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team createdTeam = teamService.createTeam(team);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long teamId) {
        Team team = teamService.getTeamById(teamId);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{teamId}/project/{projectId}")
    public ResponseEntity<String> updateTeamProjectId(@PathVariable Long teamId, @RequestBody Project project) {
        teamService.updateTeamProjectId(teamId, project);
        return ResponseEntity.ok("Projeto atualizado com sucesso!");
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Team>> getTeamsByName(@PathVariable String name) {
        List<Team> teams = teamService.findByNameIgnoreCase(name);
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Team>> getTeamsByProjectId(@PathVariable Long projectId) {
        List<Team> teams = teamService.findByProjectProjectId(projectId);
        return ResponseEntity.ok(teams);
    }
}
