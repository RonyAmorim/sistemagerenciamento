package br.com.sistemagerenciamento.repository;

import br.com.sistemagerenciamento.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    // Buscar time por nome (ignorando maiúsculas/minúsculas)
    Optional<Team> findByNameIgnoreCase(String name);

    // Buscar times de um projeto específico
    List<Team> findByProjectId(Long projectId);

    // Verificar se existe um time com o nome fornecido (ignorando maiúsculas/minúsculas) dentro de um projeto
    boolean existsByNameIgnoreCaseAndProjectId(String name, Long projectId);

    boolean existsByTeamId(Long teamId);
}