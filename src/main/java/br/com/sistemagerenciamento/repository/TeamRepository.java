package br.com.sistemagerenciamento.repository;

import br.com.sistemagerenciamento.domain.Project;
import br.com.sistemagerenciamento.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    // Buscar time por nome (ignorando maiúsculas/minúsculas)
    List<Team> findByNameIgnoreCase(String name);

    // Buscar times de um projeto específico
    List<Team> findByProjectProjectId(Long projectId);

    // Verificar se existe um time com o nome fornecido (ignorando maiúsculas/minúsculas) dentro de um projeto
    boolean existsByNameIgnoreCaseAndProjectProjectId(String name, Long projectId);

    // Verificar se a equipe existe
    boolean existsByTeamId(Long teamId);

    // Atualizar equipe por ID
    @Modifying
    @Transactional
    @Query("UPDATE Team t SET t.project = :project WHERE t.teamId = :teamId")
    void updateTeamProjectId(@Param("teamId") Long teamId, @Param("project") Project project);

    // Deletar equipes por ID do projeto
    @Modifying
    @Transactional
    @Query("DELETE FROM Team t WHERE t.project.projectId = :projectId")
    void deleteTeamsByProjectId(@Param("projectId") Long projectId);
}
