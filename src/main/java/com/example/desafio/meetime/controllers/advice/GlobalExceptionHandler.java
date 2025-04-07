package com.example.desafio.meetime.controllers.advice;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FeignException.Forbidden.class)
    public ResponseEntity<?> handleFeignForbiddenException(FeignException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Acesso negado ao HubSpot: " + ex.getMessage());
    }

    @ExceptionHandler(FeignException.BadGateway.class)
    public ResponseEntity<?> handleFeignBadRequestException(FeignException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Chamada incorreta ao HubSpot: " + ex.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> handleFeignException(FeignException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro inesperado ao conectar com o HubSpot: " + ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro inesperado: " + ex.getMessage());
    }
}
