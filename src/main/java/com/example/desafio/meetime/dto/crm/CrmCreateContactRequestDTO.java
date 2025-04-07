package com.example.desafio.meetime.dto.crm;

import jakarta.validation.constraints.Email;

public record CrmCreateContactRequestDTO(@Email String email,
                                         String nome,
                                         String sobrenome) {
}
