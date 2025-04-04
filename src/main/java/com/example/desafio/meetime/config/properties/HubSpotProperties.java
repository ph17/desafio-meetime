package com.example.desafio.meetime.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("hubspot")
public class HubSpotProperties {
    private HubSpotAuthProperties auth;
}
