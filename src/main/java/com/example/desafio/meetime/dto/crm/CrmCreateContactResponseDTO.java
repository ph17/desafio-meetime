package com.example.desafio.meetime.dto.crm;

public record CrmCreateContactResponseDTO(
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

