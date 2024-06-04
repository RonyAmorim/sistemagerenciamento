package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.Team;
import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.domain.UserTeam;
import br.com.sistemagerenciamento.domain.UserTeamId;
import br.com.sistemagerenciamento.repository.TeamRepository;
import br.com.sistemagerenciamento.repository.UserRepository;
import br.com.sistemagerenciamento.repository.UserTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTeamService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserTeamRepository userTeamRepository;


    // Método para adicionar um usuário a uma equipe
    @Transactional
    public UserTeam addUserToTeam(Long userId, Long teamId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));

        UserTeamId userTeamId = new UserTeamId(userId, teamId);
        UserTeam userTeam = new UserTeam();
        userTeam.setId(userTeamId);
        userTeam.setUser(user);
        userTeam.setTeam(team);

        userTeamRepository.save(userTeam);
        return userTeam;
    }

    // Método para remover um usuário de uma equipe
    public void removeUserFromTeam(Long userId, Long teamId) {
        UserTeamId userTeamId = new UserTeamId(userId, teamId);
        userTeamRepository.deleteById(userTeamId);
    }

    // Método para verificar se um usuário pertence a uma equipe
    public boolean isUserInTeam(Long userId, Long teamId) {
        UserTeamId userTeamId = new UserTeamId(userId, teamId);
        return userTeamRepository.existsById(userTeamId);
    }

    // Método para listar todos os usuários de uma equipe
    /*public List<User> getUsersByTeamId(Long teamId) {
        List<UserTeam> userTeams = userTeamRepository.findByTeamTeamId(teamId);
        return userTeams.stream()
                .map(UserTeam::getUser)
                .collect(Collectors.toList());
    }*/

    public List<Team> listTeamsByUser(Long userId) {
        List<UserTeam> userTeams = userTeamRepository.findByIdUserId(userId);
        return userTeams.stream()
                .map(UserTeam::getTeam)
                .collect(Collectors.toList());
    }

    public List<User> listUsersByTeam(Long teamId) {
        List<UserTeam> userTeams = userTeamRepository.findByIdTeamId(teamId);
        return userTeams.stream()
                .map(UserTeam::getUser)
                .collect(Collectors.toList());
    }
}
