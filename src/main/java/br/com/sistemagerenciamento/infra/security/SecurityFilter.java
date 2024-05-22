package br.com.sistemagerenciamento.infra.security;

import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    // Método que intercepta a requisição e faz a validação do token JWT
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Recupera o token do cabeçalho da requisição
        String token = recoverToken(request);
        if (token != null && tokenService.validateToken(token) != null) {
            String email = tokenService.validateToken(token);

            // Busca o usuário pelo email e cria a autenticação
            Optional<User> optionalUsuario = userRepository.findByEmail(email);

            // Se o usuário existe, cria a autenticação
            if (optionalUsuario.isPresent()) {
                User usuario = optionalUsuario.get();

                // Cria as autoridades
                // O tipo do usuário é a regra de negócio que determina as permissões
                var authorities = List.of(new SimpleGrantedAuthority(usuario.getType()));

                // Cria a autenticação
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // Continua a execução da requisição
        filterChain.doFilter(request, response);
    }

    // Método que recupera o token do cabeçalho da requisição
    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        // Verifica se o cabeçalho Authorization existe e se começa com "Bearer "
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}