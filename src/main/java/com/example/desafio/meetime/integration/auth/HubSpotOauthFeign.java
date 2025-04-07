package com.example.desafio.meetime.integration.auth;

import com.example.desafio.meetime.integration.auth.response.HubAuthFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth", url = "https://api.hubapi.com/oauth")
public interface HubSpotOauthFeign {

    @PostMapping(value = "/v1/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    HubAuthFeignResponse authorizeUser(@RequestBody MultiValueMap<String, String> form);
}
