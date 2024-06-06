package br.com.sistemagerenciamento.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public String root() {
        return "Bem-vindo ao sistema de gerenciamento API!";
    }
}