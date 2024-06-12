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


    /**
     * Metodo para buscar um usuario pelo ID
     * @param id ID do usuario
     * @return Usuario
     */
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nenhum usuário encontrado com o ID: " + id));
    }


    public List<UserWithoutPassword> listUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToUserWithoutPassword)
                .collect(Collectors.toList());
    }

    /** Método para buscar um usuário por email sem retornar a senha
     *
     * @param email
     * @return
     */
    public UserWithoutPassword findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Nenhum usuário encontrado com o email: " + email));
        return convertToUserWithoutPassword(user);
    }

    /** Metodo para login de um usuario
     *
     *
     */

    public User loginByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Nenhum usuário encontrado com o email: " + email));
    }

    /** Metodo para buscar um usuario pelo nome
     *
     * @param name
     * @return
     */
    public List<UserWithoutPassword> findByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToUserWithoutPassword)
                .collect(Collectors.toList());
    }

    /** Metodo para buscar um usuario pelo tipo
     *
     * @param type
     * @return
     */
    public List<UserWithoutPassword> findByType(String type) {
        return userRepository.findByType(type).stream()
                .map(this::convertToUserWithoutPassword)
                .collect(Collectors.toList());
    }

    /** Metodo para verificar se um usuario existe pelo email
     *
     * @param email
     * @return
     */
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /** Metodo para atualizar um usuario
     *
     * @param email
     * @param userDto
     * @return
     */
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

        return userRepository.save(existingUser);
    }

    /** Metodo para atualizar a senha de um usuario
     *
     * @param email
     * @param oldPassword
     * @param newPassword
     */
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

    /** Metodo para atualizar o tipo de um usuario
     *
     * @param email
     * @param newType
     */
    public void updateType(String email, String newType) {
        userRepository.updateTypeByEmail(newType, email);
    }

    /** Metodo para deletar um usuario pelo email
     *
     * @param email
     */
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    /** Metodo conversor de User para UserWithoutPassword
     *
     * @param user
     */
    public UserWithoutPassword convertToUserWithoutPassword(User user) {
        return new UserWithoutPassword(user.getUserId(), user.getName(), user.getEmail(), user.getType());
    }
}
