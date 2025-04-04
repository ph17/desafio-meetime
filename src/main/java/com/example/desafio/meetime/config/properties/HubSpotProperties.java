package com.example.desafio.meetime.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("hubspot")
@Data
public class HubSpotProperties {
    private HubSpotAuthProperties auth;
}
