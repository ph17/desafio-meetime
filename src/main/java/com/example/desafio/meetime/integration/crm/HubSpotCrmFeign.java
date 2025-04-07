package com.example.desafio.meetime.integration.crm;

import com.example.desafio.meetime.integration.crm.request.HubCrmCreateContactRequest;
import com.example.desafio.meetime.integration.crm.response.HubCrmCreateContactResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "crm", url = "https://api.hubapi.com/crm/v3/objects")
public interface HubSpotCrmFeign {

    @PostMapping("/contacts")
    HubCrmCreateContactResponse createContact(@RequestHeader("Authorization") String authHeader,
                                              @RequestBody HubCrmCreateContactRequest contact);
}
