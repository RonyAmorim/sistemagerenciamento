package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.domain.Team;
import br.com.sistemagerenciamento.dto.Team.TeamRegisterRequestDTO;
import br.com.sistemagerenciamento.dto.Team.TeamResponseDTO;
import br.com.sistemagerenciamento.dto.user.UserResponseDTO;
import br.com.sistemagerenciamento.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<?> createTeam(@Valid @RequestBody TeamRegisterRequestDTO teamDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Team createdTeam = teamService.createTeam(teamDTO);

        // Cria o UserResponseDTO com os dados que você quer retornar
        UserResponseDTO managerResponseDTO = new UserResponseDTO(
                createdTeam.getManagerId().getUserId(),
                createdTeam.getManagerId().getName(),
                createdTeam.getManagerId().getEmail()
        );

        // Cria o TeamResponseDTO com os dados que você quer retornar
        TeamResponseDTO responseDTO = new TeamResponseDTO(
                createdTeam.getTeamId(),
                createdTeam.getName(),
                managerResponseDTO,
                (createdTeam.getProject() != null) ? createdTeam.getProject().getProjectId() : null
                );

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponseDTO> getTeamById(@PathVariable Long teamId) {
        Team team = teamService.getTeamById(teamId);
        return ResponseEntity.ok(convertToTeamResponseDTO(team));
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        List<TeamResponseDTO> responseDTOs = teams.stream()
                .map(this::convertToTeamResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<String> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.ok("Equipe deletada com sucesso!");
    }

    @PutMapping("/{teamId}/project/{projectId}")
    public ResponseEntity<String> updateTeamProjectId(@PathVariable Long teamId, @RequestBody Project project) {
        teamService.updateTeamProjectId(teamId, project);
        return ResponseEntity.ok("Equipe com projeto atualizado com sucesso!");
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<TeamResponseDTO>> getTeamsByName(@PathVariable String name) {
        List<Team> teams = teamService.findByNameIgnoreCase(name);
        List<TeamResponseDTO> responseDTOs = teams.stream()
                .map(this::convertToTeamResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Team>> getTeamsByProjectId(@PathVariable Long projectId) {
        List<Team> teams = teamService.findByProjectProjectId(projectId);
        return ResponseEntity.ok(teams);
    }

    private TeamResponseDTO convertToTeamResponseDTO(Team team) {
        UserResponseDTO managerResponseDTO = new UserResponseDTO(
                team.getManagerId().getUserId(),
                team.getManagerId().getName(),
                team.getManagerId().getEmail()
        );

        return new TeamResponseDTO(
                team.getTeamId(),
                team.getName(),
                managerResponseDTO,
                team.getProject() != null ? team.getProject().getProjectId() : null
        );
    }
}
