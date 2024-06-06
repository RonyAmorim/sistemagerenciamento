package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.domain.Team;
import br.com.sistemagerenciamento.domain.UserTeam;
import br.com.sistemagerenciamento.dto.team.TeamResponseDTO;
import br.com.sistemagerenciamento.dto.team.TeamWithoutProjectDTO;
import br.com.sistemagerenciamento.dto.user.UserResponseDTO;
import br.com.sistemagerenciamento.dto.userteam.UserTeamResponseDTO;
import br.com.sistemagerenciamento.service.UserTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-team")
public class UserTeamController {

    @Autowired
    private UserTeamService userTeamService;

    // Adicionar usuário a um equipe
    @PostMapping("/{teamId}/users/{userId}")
    public ResponseEntity<UserTeamResponseDTO> addUserToTeam(@PathVariable Long teamId, @PathVariable Long userId) {
        UserTeam userTeam = userTeamService.addUserToTeam(userId, teamId);
        return ResponseEntity.ok(convertToUserTeamResponseDTO(userTeam));
    }


    // Remover usuário de um equipe
    @DeleteMapping("/{teamId}/users/{userId}")
    public ResponseEntity<Void> removeUserFromTeam(@PathVariable Long teamId, @PathVariable Long userId) {
        userTeamService.removeUserFromTeam(userId, teamId);
        return ResponseEntity.noContent().build();
    }

    // Listar equipe aos quais um usuário pertence
    @GetMapping("/users/{userId}/teams")
    public ResponseEntity<List<TeamWithoutProjectDTO>> listTeamsByUser(@PathVariable Long userId) {
        List<Team> teams = userTeamService.listTeamsByUser(userId);

        if (teams.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content se não houver times
        }

        List<TeamWithoutProjectDTO> teamDTOs = teams.stream()
                .map(this::convertToTeamWithoutProjectDTO)
                .collect(Collectors.toList());


        return ResponseEntity.ok(teamDTOs);
    }


    // Listar usuários que pertencem a uma equipe
    @GetMapping("/teams/{teamId}/users")
    public ResponseEntity<List<UserResponseDTO>> listUsersByTeam(@PathVariable Long teamId) {
        List<User> users = userTeamService.listUsersByTeam(teamId); // Obter a lista de usuários do serviço
        List<UserResponseDTO> userDTOs = users.stream() // Converter a lista de User para UserResponseDTO
                .map(this::convertToUserResponseDTO) // Mapeando cada User para UserResponseDTO
                .toList(); // Coleta os UserResponseDTOs em uma lista
        return ResponseEntity.ok(userDTOs); // Retornar a lista de DTOs
    }

    // Método para converter User em UserResponseDTO (adicione ao seu UserTeamController)
    private UserResponseDTO convertToUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail()
        );
    }

    // Método no UserTeamController
    private UserTeamResponseDTO convertToUserTeamResponseDTO(UserTeam userTeam) {
        return new UserTeamResponseDTO(
                new UserResponseDTO(
                        userTeam.getUser().getUserId(), // Supondo que o UserTeam tenha um método getUser()
                        userTeam.getUser().getName(),
                        userTeam.getUser().getEmail()
                ),
                new TeamWithoutProjectDTO(
                        userTeam.getTeam().getTeamId(), // Supondo que o UserTeam tenha um método getTeam()
                        userTeam.getTeam().getName()
                )
        );
    }

    // Método no UserTeamController
    private TeamWithoutProjectDTO convertToTeamWithoutProjectDTO(Team team) {
        return new TeamWithoutProjectDTO(
                team.getTeamId(), // Supondo que o Team tenha um método getTeamId()
                team.getName()   // Supondo que o Team tenha um método getName()
        );
    }


}