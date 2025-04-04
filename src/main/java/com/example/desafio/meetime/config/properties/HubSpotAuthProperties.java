package com.example.desafio.meetime.config.properties;


import java.util.List;


public record HubSpotAuthProperties(String url,
                                    String clientId,
                                    String redirectUri,
                                    List<String> requiredScopes,
                                    List<String> optionalScopes) {
}
