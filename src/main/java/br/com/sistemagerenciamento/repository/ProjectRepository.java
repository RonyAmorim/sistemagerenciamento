package br.com.sistemagerenciamento.repository;

import br.com.sistemagerenciamento.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Buscar projeto por ID
    Optional<Project> findById(Long id);

    // Buscar projetos por nome (ignorando maiúsculas/minúsculas)
    List<Project> findByNameContainingIgnoreCase(String name);

    // Buscar projetos por status
    List<Project> findByStatus(String status);

    // Verificar se existe um projeto com o nome fornecido (ignorando maiúsculas/minúsculas)
    boolean existsByNameIgnoreCase(String name);

    // Atualizar o status de um projeto por ID (usando @Modifying e @Query)
    @Modifying
    @Transactional
    @Query("UPDATE Project p SET p.status = :status WHERE p.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") String status);

    // Atualizar a descrição de um projeto por ID (usando @Modifying e @Query)
    @Modifying
    @Transactional
    @Query("UPDATE Project p SET p.description = :description WHERE p.id = :id")
    void updateDescriptionById(@Param("id") Long id, @Param("description") String description);

    @Modifying
    @Transactional
    @Query("DELETE FROM Project p WHERE p.id = :id")
    void deleteById(@Param("id") Long id);
}
