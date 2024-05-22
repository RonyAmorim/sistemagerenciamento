package br.com.sistemagerenciamento.infra.security;

import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Autowired
    private UserRepository userRepository;

    @Value("${api.security.token.secret}")
    private String secret;

    //metodo para gerar token
    public String generateToken(String email) {
        // Busca o usuário no banco de dados e lança uma exceção caso não encontre
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Gera o token JWT com o email do usuário
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("sistemagerenciamento")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    //metodo para validar token
    public String validateToken(String token) {
        // Verifica se o token é válido e se o usuário existe
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String email = JWT.require(algorithm)
                    .withIssuer("sistemagerenciamento")
                    .build()
                    .verify(token)
                    .getSubject();

            //caso o email exista no banco de dados
            if (userRepository.existsByEmail(email)) {
                // Token válido e usuário existe
                return email;
            } else {
                // Token válido, mas usuário não existe mais
                return null;
            }
        } catch (JWTVerificationException exception) {
            // Token inválido ou expirado
            return null;
        }
    }

    //metodo para gerar data de expiração
    private Instant generateExpirationDate() {
        // Retorna a data atual mais 2 horas
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}