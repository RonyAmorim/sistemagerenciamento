package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testListarTodosUsuarios() {
        when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
        List<User> usuarios = userService.listUsers();
        assertThat(usuarios).hasSize(2);
    }

    @Test
    void testFindByEmail() {
        User usuario = new User();
        usuario.setEmail("teste@email.com");
        when(userRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));

        User usuarioEncontrado = userService.findByEmail("teste@email.com");
        assertThat(usuarioEncontrado).isEqualTo(usuario);
    }

    @Test
    void testFindByEmailNaoEncontrado() {
        when(userRepository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.findByEmail("naoexiste@email.com"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Nenhum usuário encontrado com o email: naoexiste@email.com"); // Corrigida a mensagem
    }

}