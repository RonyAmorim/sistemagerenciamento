package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.dto.user.LoginRequestDTO;
import br.com.sistemagerenciamento.dto.user.RegisterRequestDTO;
import br.com.sistemagerenciamento.dto.user.ResponseDTO;
import br.com.sistemagerenciamento.dto.user.UserWithoutPassword;
import br.com.sistemagerenciamento.infra.security.TokenService;
import br.com.sistemagerenciamento.repository.UserRepository;
import br.com.sistemagerenciamento.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        User usuario = userRepository.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (passwordEncoder.matches(body.password(), usuario.getPassword())) {
            String token = tokenService.generateToken(usuario.getEmail()); // Passamos o email para o TokenService
            UserWithoutPassword user = userService.findByEmail(usuario.getEmail());

            ResponseEntity<ResponseDTO> ok = ResponseEntity.ok(new ResponseDTO(user.id(), user.username(), user.type()));
            return ok;
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
        novoUsuario.setCreationDate(LocalDate.now());

        userRepository.save(novoUsuario);

        UserWithoutPassword user = userService.findByEmail(novoUsuario.getEmail());

        String token = tokenService.generateToken(novoUsuario.getEmail());
        ResponseEntity<ResponseDTO> ok = ResponseEntity.ok(new ResponseDTO(user.id(), user.username(), user.type()));
        return ok;
    }
}