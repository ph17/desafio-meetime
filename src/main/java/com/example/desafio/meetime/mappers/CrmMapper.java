package com.example.desafio.meetime.mappers;

import com.example.desafio.meetime.dto.crm.CrmCreateContactRequestDTO;
import com.example.desafio.meetime.dto.crm.CrmCreateContactResponseDTO;
import com.example.desafio.meetime.integration.crm.request.HubCrmContactProperties;
import com.example.desafio.meetime.integration.crm.request.HubCrmCreateContactRequest;
import com.example.desafio.meetime.integration.crm.response.HubCrmCreateContactResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CrmMapper {
    CrmMapper INSTANCE = Mappers.getMapper(CrmMapper.class);

    CrmCreateContactResponseDTO toDto(HubCrmCreateContactResponse response);

    @Mapping(source = "nome", target = "firstname")
    @Mapping(source = "sobrenome", target = "lastname")
    HubCrmContactProperties toProperties(CrmCreateContactRequestDTO response);

}
