package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.dto.user.UserWithoutPassword;
import br.com.sistemagerenciamento.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Método para retornar todos os usuários sem senha
    public List<UserWithoutPassword> listUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToUserWithoutPassword)
                .collect(Collectors.toList());
    }

    // Método para buscar um usuário por email sem retornar a senha
    public UserWithoutPassword findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Nenhum usuário encontrado com o email: " + email));
        return convertToUserWithoutPassword(user);
    }

    //metodo para login de um usuario
    public User loginByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Nenhum usuário encontrado com o email: " + email));
    }

    // Método para buscar usuários por nome sem retornar a senha
    public List<UserWithoutPassword> findByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToUserWithoutPassword)
                .collect(Collectors.toList());
    }

    // Método para buscar usuários por tipo sem retornar a senha
    public List<UserWithoutPassword> findByType(String type) {
        return userRepository.findByType(type).stream()
                .map(this::convertToUserWithoutPassword)
                .collect(Collectors.toList());
    }

    // Método para verificar se um usuário existe
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Método para atualizar a senha de um usuário
    public void updatePassword(String email, String newPassword) {
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

    // Método de conversão de User para UserWithoutPassword
    private UserWithoutPassword convertToUserWithoutPassword(User user) {
        return new UserWithoutPassword(user.getUserId(), user.getName(), user.getEmail(), user.getType());
    }
}
