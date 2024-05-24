package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.dto.user.UserWithoutPassword;
import br.com.sistemagerenciamento.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserWithoutPassword>> listarUsuarios() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserWithoutPassword> buscarUsuarioPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String email) {
        userService.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }
}