package com.example.desafio.meetime.controllers;

import com.example.desafio.meetime.dto.AuthorizationResponseDTO;
import com.example.desafio.meetime.integration.response.HubAuthFeignResponse;
import com.example.desafio.meetime.mappers.AuthorizationMapper;
import com.example.desafio.meetime.services.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
