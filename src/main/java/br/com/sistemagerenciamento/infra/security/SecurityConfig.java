package br.com.sistemagerenciamento.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    // Configuração de segurança para as requisições
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Desabilita o CSRF e configura a política de sessão para STATELESS
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configura a autorização das requisições
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                // Adiciona o filtro de segurança antes do filtro de autenticação
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(customUserDetailsService) // Configura o UserDetailsService
                .build();
    }

    @Bean
    // Configura o PasswordEncoder
    public PasswordEncoder passwordEncoder() {
        // Retorna o BCryptPasswordEncoder
        return new BCryptPasswordEncoder();
    }

    @Bean
    // Configura o AuthenticationManager
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
