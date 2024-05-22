package br.com.sistemagerenciamento.infra.security;

import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TokenService tokenService;

    private String secret = "Kl9s#1lPz$Qo&3a";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenService, "secret", secret);
    }

    @Test
    void testGerarToken() {
        ReflectionTestUtils.setField(tokenService, "secret", "Kl9s#1lPz$Qo&3a");

        User usuario = new User();
        usuario.setEmail("teste@email.com");
        when(userRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));

        String token = tokenService.generateToken("teste@email.com");
        assertThat(token).isNotBlank(); // Verifica se o token não é vazio
    }

    @Test
    void testValidarTokenValido() {
        User usuario = new User();
        usuario.setEmail("teste@email.com");

        when(userRepository.existsByEmail("teste@email.com")).thenReturn(true);

        String token = tokenService.generateToken("teste@email.com");

        String email = tokenService.validateToken(token);
        assertThat(email).isEqualTo("teste@email.com");
    }

    @Test
    void testValidarTokenInvalido() {
        // Gera um token inválido
        String token = "token_invalido";

        // Valida o token
        String email = tokenService.validateToken(token);

        // Verifica se o email retornado é null
        assertThat(email).isNull();
    }

    @Test
    void testValidarTokenExpirado() {
        // Cria um usuário mockado
        User usuario = new User();
        usuario.setEmail("teste@email.com");

        // Gera um token expirado
        String token = gerarTokenExpirado(usuario);

        // Valida o token
        String email = tokenService.validateToken(token);

        // Verifica se o email retornado é null
        assertThat(email).isNull();
    }


    // Método auxiliar para gerar um token válido
    private String gerarTokenValido(User usuario) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("seu-projeto")
                .withSubject(usuario.getEmail())
                .withExpiresAt(Date.from(Instant.now().plus(1, ChronoUnit.HOURS))) // Expira em 1 hora
                .sign(algorithm);
    }

    // Método auxiliar para gerar um token expirado
    private String gerarTokenExpirado(User usuario) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("seu-projeto")
                .withSubject(usuario.getEmail())
                .withExpiresAt(Date.from(Instant.now().minus(1, ChronoUnit.HOURS))) // Expira há 1 hora
                .sign(algorithm);
    }
}
