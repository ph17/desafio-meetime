package com.example.desafio.meetime.integration.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HubAuthFeignResponse(@JsonProperty("refresh_token") String refreshToken,
                                   @JsonProperty("access_token") String accessToken,
                                   @JsonProperty("expires_in") Integer expiresIn) {
}
