package com.financial.loans.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record LoanRequestDTO(
    @NotBlank(message = "El número de operación es requerido")
    @Size(max = 50, message = "El número de operación no puede exceder 50 caracteres")
    String operationNumber,
    
    @NotBlank(message = "El canal es requerido")
    @Size(max = 30, message = "El canal no puede exceder 30 caracteres")
    String channel,
    
    @NotNull(message = "El monto es requerido")
    @Positive(message = "El monto debe ser positivo")
    BigDecimal amount,
    
    @NotBlank(message = "El ID del cliente es requerido")
    @Size(max = 20, message = "El ID del cliente no puede exceder 20 caracteres")
    String customerId
) {
    public String buildIdempotencyKey() {
        return operationNumber + ":" + channel;
    }
}