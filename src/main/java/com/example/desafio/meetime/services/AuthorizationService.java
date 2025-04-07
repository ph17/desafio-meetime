package com.example.desafio.meetime.services;

import com.example.desafio.meetime.config.properties.HubSpotProperties;
import com.example.desafio.meetime.dto.auth.AuthorizationResponseDTO;
import com.example.desafio.meetime.integration.auth.HubSpotOauthFeign;
import com.example.desafio.meetime.integration.auth.response.HubAuthFeignResponse;
import com.example.desafio.meetime.mappers.AuthorizationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final HubSpotProperties properties;
    private final HubSpotOauthFeign feign;

    public String gerarUrl() {
        var authProp = properties.getAuth();
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .path(authProp.url())
                .queryParam("client_id", authProp.clientId())
                .queryParam("redirect_uri", authProp.redirectUri())
                .queryParam("scope", String.join("%20", authProp.requiredScopes()))
                .queryParam("optional_scope", authProp.optionalScopes())
                .build()
                .toUriString();
    }

    public AuthorizationResponseDTO authorizeUser(String code) {
        var authProp = properties.getAuth();

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();

        form.add("grant_type", authProp.grantType());
        form.add("client_id", authProp.clientId());
        form.add("client_secret", authProp.clientSecret());
        form.add("redirect_uri", authProp.redirectUri());
        form.add("code", code);

        HubAuthFeignResponse feignResponse = feign.authorizeUser(form);

        return AuthorizationMapper.INSTANCE.toAuthorizationDTO(feignResponse);
    }
}
