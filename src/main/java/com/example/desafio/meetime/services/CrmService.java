package com.example.desafio.meetime.services;

import com.example.desafio.meetime.dto.crm.CrmCreateContactRequestDTO;
import com.example.desafio.meetime.dto.crm.CrmCreateContactResponseDTO;
import com.example.desafio.meetime.integration.crm.HubSpotCrmFeign;
import com.example.desafio.meetime.integration.crm.request.HubCrmCreateContactRequest;
import com.example.desafio.meetime.mappers.CrmMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CrmService {

    private final HubSpotCrmFeign feign;

    public CrmCreateContactResponseDTO criarContato(String token, CrmCreateContactRequestDTO request){
        var properties = CrmMapper.INSTANCE.toProperties(request);
        HubCrmCreateContactRequest feignRequest = new HubCrmCreateContactRequest(properties);

        var feignResponse = feign.createContact(token, feignRequest);
        return CrmMapper.INSTANCE.toDto(feignResponse);
    }
}
