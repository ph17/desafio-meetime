package com.example.desafio.meetime.controllers;

import com.example.desafio.meetime.services.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService service;

    @GetMapping("/gerar-url")
    public String gerarUrl(){
        return service.gerarUrl();
    }
}
