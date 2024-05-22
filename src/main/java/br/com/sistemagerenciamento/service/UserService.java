package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Método para retornar todos os usuários
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    // Método para salvar um usuário
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Nenhum usuário encontrado com o email: " + email));
    }

    // Método para buscar um usuário por nome
    public List<User> findByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    // Método para buscar um usuário por tipo
    public List<User> findByType(String type) {
        return userRepository.findByType(type);
    }

    // Método para verificar se um usuário existe
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Método para atualizar a senha de um usuário
    public void uptadePassword(String email, String newPassword) {
        userRepository.updatePasswordByEmail(newPassword, email);
    }

    // Método para atualizar o tipo de um usuário
    public void updateType(String email, String newType) {
        userRepository.updateTypeByEmail(newType, email);
    }

    // Método para excluir um usuário pelo seu email
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }
}