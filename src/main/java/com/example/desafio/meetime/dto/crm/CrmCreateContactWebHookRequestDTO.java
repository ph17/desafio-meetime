package com.example.desafio.meetime.dto.crm;

public record CrmCreateContactWebHookRequestDTO(Long appId,
                                                Integer eventId,
                                                Long subscriptionId,
                                                Long portalId,
                                                Long occurredAt,
                                                String subscriptionType,
                                                Integer attemptNumber,
                                                Long objectId,
                                                String changeSource,
                                                String changeFlag) {
}
