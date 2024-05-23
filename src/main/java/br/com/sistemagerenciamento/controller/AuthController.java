package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.dto.LoginRequestDTO;
import br.com.sistemagerenciamento.dto.RegisterRequestDTO;
import br.com.sistemagerenciamento.dto.ResponseDTO;
import br.com.sistemagerenciamento.infra.security.TokenService;
import br.com.sistemagerenciamento.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        User usuario = userRepository.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (passwordEncoder.matches(body.password(), usuario.getPassword())) {
            String token = tokenService.generateToken(usuario.getEmail()); // Passamos o email para o TokenService
            return ResponseEntity.ok(new ResponseDTO(usuario.getName() , token));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
        if (userRepository.existsByEmail(body.email())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        User novoUsuario = new User();
        novoUsuario.setName(body.name());
        novoUsuario.setEmail(body.email());
        novoUsuario.setPassword(passwordEncoder.encode(body.password()));
        novoUsuario.setType(body.type());
        novoUsuario.setCreationDate(LocalDateTime.now());

        userRepository.save(novoUsuario);

        String token = tokenService.generateToken(novoUsuario.getEmail());
        return ResponseEntity.ok(new ResponseDTO(novoUsuario.getName(), token));
    }
}