package com.example.desafio.meetime.controllers.crm;

import com.example.desafio.meetime.dto.crm.CrmCreateContactRequestDTO;
import com.example.desafio.meetime.dto.crm.CrmCreateContactResponseDTO;
import com.example.desafio.meetime.dto.crm.CrmCreateContactWebHookRequestDTO;
import com.example.desafio.meetime.services.CrmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/crm")
@RequiredArgsConstructor
public class CrmController {

    private final CrmService service;

    @PostMapping("/criar-contato")
    @ResponseStatus(HttpStatus.CREATED)
    CrmCreateContactResponseDTO criarContato(@RequestHeader("Authorization") String token,
                                             @RequestBody CrmCreateContactRequestDTO requestDTO){
        return service.criarContato(token, requestDTO);
    }

    @PostMapping("/criar-contato-webhook")
    @ResponseStatus(HttpStatus.OK)
    void criarContato(@RequestBody CrmCreateContactWebHookRequestDTO requestDTO){
        log.info("Webhook recebido com sucesso! Body recebido: {}", requestDTO);
    }
}
