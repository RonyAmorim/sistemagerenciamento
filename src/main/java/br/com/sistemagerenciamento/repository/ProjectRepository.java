package br.com.sistemagerenciamento.repository;

import br.com.sistemagerenciamento.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectRepository  extends JpaRepository<Project, Long> {
    // Método para buscar um projeto por nome
    List<Project> findByNameContainingIgnoreCase(String name);

    // Método para buscar um projeto por status
    List<Project> findByStatus(String status);

    // Método para verificar se um projeto existe
    boolean existsByName(String name);

    // Método para verificar se um projeto existe ignorando o case
    boolean existsByNameIgnoreCase(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Project p SET p.status = :status WHERE p.name = :name")
    // Método para atualizar o status de um projeto
    void updateStatusByName(@Param("name") String name, @Param("status") String status);

    @Modifying
    @Transactional
    @Query("DELETE FROM Project p WHERE p.name = :name")
    // Método para excluir um projeto por nome
    void deleteByName(String name);
}
