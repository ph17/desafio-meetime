package com.example.desafio.meetime.services;

import com.example.desafio.meetime.config.properties.HubSpotProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final HubSpotProperties properties;

    public String gerarUrl() {
        var authProp = properties.getAuth();
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .path(authProp.url())
                .queryParam("client_id", authProp.clientId())
                .queryParam("redirect_uri", authProp.redirectUri())
                .queryParam("scope", authProp.requiredScopes())
                .queryParam("optional_scope", authProp.optionalScopes())
                .build()
                .toUriString();
    }
}
