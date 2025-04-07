package com.example.desafio.meetime.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthorizationResponseDTO(@JsonProperty("refresh_token") String refreshToken,
                                       @JsonProperty("access_token") String accessToken,
                                       @JsonProperty("expires_in") Integer expiresIn) {
}
