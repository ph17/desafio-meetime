package com.example.desafio.meetime.controllers.crm;

import com.example.desafio.meetime.dto.crm.CrmCreateContactRequestDTO;
import com.example.desafio.meetime.dto.crm.CrmCreateContactResponseDTO;
import com.example.desafio.meetime.services.CrmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crm")
@RequiredArgsConstructor
public class CrmController {

    private final CrmService service;

    @PostMapping("/criar-contato")
    CrmCreateContactResponseDTO criarContato(@RequestHeader("Authorization") String token,
                                             @RequestBody CrmCreateContactRequestDTO requestDTO){
        return service.criarContato(token, requestDTO);
    }
}
