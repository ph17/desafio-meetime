package com.example.desafio.meetime.integration.crm.response;

public record HubCrmCreateContactResponse(
        String createdAt,
        Boolean archived,
        String archivedAt,
        Object propertiesWithHistory,
        String id,
        String objectWriteTraceId,
        Object properties,
        String updatedAt
) {
}

