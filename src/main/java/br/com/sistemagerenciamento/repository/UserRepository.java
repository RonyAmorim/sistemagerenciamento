package br.com.sistemagerenciamento.repository;

import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.dto.user.UserWithoutPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //Metodo para buscar um usuario por email
    Optional<User> findByEmail(String email);

    // Método para buscar um usuário por ID
    Optional<User> findById(Long id);

    // Método para buscar todos os usuário por nome
    List<User> findByNameContainingIgnoreCase(String name);

    // Método para buscar todos os usuário por tipo
    List<User> findByType(String tipo);

    // Método para verificar se um usuário existe
    boolean existsByEmail(String email);

    @Modifying // Anotação para indicar que o método modifica o banco de dados
    @Transactional
    @Query("UPDATE User u SET u.password = :password WHERE u.email = :email")
    // Método para atualizar a senha de um usuário
    void updatePasswordByEmail( @Param("email") String email, @Param("password") String password);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.type = :type WHERE u.email = :email")
    // Método para atualizar o tipo de um usuário
    void updateTypeByEmail( @Param("email") String email, @Param("type") String type);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.email = :email")
    // Método para excluir um usuário por email
    void deleteByEmail(String email);
}
