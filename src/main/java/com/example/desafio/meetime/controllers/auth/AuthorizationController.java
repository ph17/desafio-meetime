package com.example.desafio.meetime.controllers.auth;

import com.example.desafio.meetime.dto.auth.AuthorizationResponseDTO;
import com.example.desafio.meetime.services.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService service;

    @GetMapping("/gerar-url")
    @ResponseStatus(HttpStatus.OK)
    public String gerarUrl(){
        return service.gerarUrl();
    }

    @GetMapping("/oauth-callback")
    @ResponseStatus(HttpStatus.OK)
    public AuthorizationResponseDTO oauthCallback(@RequestParam("code") String code){
        return service.authorizeUser(code);
    }
}
