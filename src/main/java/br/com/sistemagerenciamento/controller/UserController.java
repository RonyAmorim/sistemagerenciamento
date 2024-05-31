package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.dto.user.UpdatePasswordRequestDTO;
import br.com.sistemagerenciamento.dto.user.UpdateUserRequestDTO;
import br.com.sistemagerenciamento.dto.user.UserWithoutPassword;
import br.com.sistemagerenciamento.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<String> deletarUsuario(@PathVariable String email) {
        userService.deleteByEmail(email);
        return ResponseEntity.ok("Usuario deletado com sucesso!");
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserWithoutPassword> updateUser(@PathVariable String email, @RequestBody @Valid UpdateUserRequestDTO userDto) {
        User updatedUser = userService.updateUser(email, userDto); // Implementar no UserService
        return ResponseEntity.ok(userService.convertToUserWithoutPassword(updatedUser));
    }

    @PutMapping("/{email}/password") // Novo endpoint para atualizar senha
    public ResponseEntity<String> updatePassword(@PathVariable String email, @RequestBody @Valid UpdatePasswordRequestDTO passwordDto) {
        userService.updatePassword(email, passwordDto.oldPassword(), passwordDto.newPassword());
        return ResponseEntity.ok("Senha Alterada com sucesso!");
    }
}