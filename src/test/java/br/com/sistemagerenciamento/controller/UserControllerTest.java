package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarTodosUsuarios() throws Exception {
        when(userService.listUsers()).thenReturn(List.of(new User(), new User()));
        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testBuscarUsuarioPorEmail() throws Exception {
        User usuario = new User();
        usuario.setEmail("teste@email.com");
        when(userService.findByEmail("teste@email.com")).thenReturn(usuario);

        mockMvc.perform(get("/usuarios/{email}", "teste@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("teste@email.com"));
    }

    @Test
    void testDeletarUsuario() throws Exception {
        mockMvc.perform(delete("/usuarios/{email}", "teste@email.com"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testAtualizarUsuario() throws Exception {
    }
}