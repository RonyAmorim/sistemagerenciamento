package br.com.sistemagerenciamento.repository;

import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.domain.UserTeam;
import br.com.sistemagerenciamento.domain.UserTeamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, UserTeamId>{
    // Buscar usuários de uma equipe específica pelo id da equipe
    List<UserTeam> findByTeamTeamId(Long teamId);

    // Verificar se um usuário pertence a equipe
    boolean existsById(UserTeamId userTeamId);

    //verificar se a equipe existe
    boolean existsByTeamTeamId(Long teamId);

    //deletar um usuário de uma equipe
    void deleteById(UserTeamId userTeamId);


    List<UserTeam> findByIdTeamId(Long teamId);

    List<UserTeam> findByIdUserId(Long userId);
}
