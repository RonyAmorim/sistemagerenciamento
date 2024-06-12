package br.com.sistemagerenciamento.repository;

import br.com.sistemagerenciamento.domain.Impediment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface que estende JpaRepository para realizar operações de CRUD no banco de dados.
 */
@Repository
public interface ImpedimentRepository extends JpaRepository<Impediment, Long> {
}
