package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.domain.Team;
import br.com.sistemagerenciamento.domain.UserTeam;
import br.com.sistemagerenciamento.service.UserTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-team")
public class UserTeamController {

    @Autowired
    private UserTeamService userTeamService;

    // Adicionar usuário a um time
    @PostMapping("/{teamId}/users/{userId}")
    public ResponseEntity<UserTeam> addUserToTeam(@PathVariable Long teamId, @PathVariable Long userId) {
        UserTeam userTeam = userTeamService.addUserToTeam(userId, teamId);
        return ResponseEntity.ok(userTeam);
    }

    // Remover usuário de um time
    @DeleteMapping("/{teamId}/users/{userId}")
    public ResponseEntity<Void> removeUserFromTeam(@PathVariable Long teamId, @PathVariable Long userId) {
        userTeamService.removeUserFromTeam(userId, teamId);
        return ResponseEntity.noContent().build();
    }

    // Listar times aos quais um usuário pertence
    @GetMapping("/users/{userId}/teams")
    public ResponseEntity<List<Team>> listTeamsByUser(@PathVariable Long userId) {
return ResponseEntity.ok(userTeamService.listTeamsByUser(userId));


    }

    // Listar usuários que pertencem a um time
    @GetMapping("/teams/{teamId}/users")
    public ResponseEntity<List<User>> listUsersByTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(userTeamService.listUsersByTeam(teamId));
    }
}