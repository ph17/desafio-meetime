package com.example.desafio.meetime.controllers.auth;

import com.example.desafio.meetime.dto.AuthorizationResponseDTO;
import com.example.desafio.meetime.services.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService service;

    @GetMapping("/gerar-url")
    public String gerarUrl(){
        return service.gerarUrl();
    }

    @GetMapping("/oauth-callback")
    public AuthorizationResponseDTO oauthCallback(@RequestParam("code") String code){
        return service.authorizeUser(code);
    }
}
