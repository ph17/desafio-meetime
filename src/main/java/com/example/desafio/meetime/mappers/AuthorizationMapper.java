package com.example.desafio.meetime.mappers;

import com.example.desafio.meetime.dto.auth.AuthorizationResponseDTO;
import com.example.desafio.meetime.integration.auth.response.HubAuthFeignResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorizationMapper {

    AuthorizationMapper INSTANCE = Mappers.getMapper(AuthorizationMapper.class);

    AuthorizationResponseDTO toAuthorizationDTO(HubAuthFeignResponse response);
}
