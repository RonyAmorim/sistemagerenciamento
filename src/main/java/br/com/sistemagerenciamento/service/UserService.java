package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.dto.user.UpdateUserRequestDTO;
import br.com.sistemagerenciamento.dto.user.UserWithoutPassword;
import br.com.sistemagerenciamento.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

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

    public User updateUser(String email, UpdateUserRequestDTO userDto) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email: " + email));

        // Atualiza os campos permitidos
        if (userDto.name() != null) {
            existingUser.setName(userDto.name());
        }
        if (userDto.email() != null) {
            // Verifica se o novo email já existe
            if (userRepository.existsByEmail(userDto.email())) {
                throw new RuntimeException("Email já cadastrado");
            }
            existingUser.setEmail(userDto.email());
        }
        if (userDto.type() != null) {
            existingUser.setType(userDto.type());
        }
        if (userDto.password() != null) {
            existingUser.setPassword(passwordEncoder.encode(userDto.password()));
        }

        return userRepository.save(existingUser);
    }

    public void updatePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email: " + email));

        // Verifica se a senha antiga está correta
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword)); // Codifica a nova senha
            userRepository.save(user);
        } else {
            throw new RuntimeException("Senha antiga incorreta");
        }
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
    public UserWithoutPassword convertToUserWithoutPassword(User user) {
        return new UserWithoutPassword(user.getUserId(), user.getName(), user.getEmail(), user.getType());
    }
}
