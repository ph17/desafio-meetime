package com.example.desafio.meetime.mappers;

import com.example.desafio.meetime.dto.auth.AuthorizationResponseDTO;
import com.example.desafio.meetime.integration.auth.response.HubAuthFeignResponse;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorizationMapper {

    AuthorizationMapper INSTANCE = Mappers.getMapper(AuthorizationMapper.class);

    @Mapping(target = "accessToken", qualifiedByName = "addBearerAccessToken")
    AuthorizationResponseDTO toAuthorizationDTO(HubAuthFeignResponse response);

    @Named("addBearerAccessToken")
    default String addBearerAccessToken(String token){
        return "Bearer " + token;
    }
}
