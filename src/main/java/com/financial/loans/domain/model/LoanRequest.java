package com.financial.loans.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {
    @NotNull(message = "El ID de la solicitud no puede ser nulo")
    private UUID id;

    @NotBlank(message = "El número de operación no puede estar vacío")
    @Size(max = 50, message = "El número de operación no puede exceder 50 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9-]*$", message = "El número de operación solo puede contener letras, números y guiones")
    private String operationNumber;

    @NotBlank(message = "El canal no puede estar vacío")
    @Size(max = 20, message = "El canal no puede exceder 20 caracteres")
    private String channel;

    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que cero")
    private BigDecimal amount;

    @NotBlank(message = "El estado no puede estar vacío")
    @Size(max = 20, message = "El estado no puede exceder 20 caracteres")
    private String status;

    @NotNull(message = "La fecha de creación no puede ser nula")
    private LocalDateTime createdAt;

    @NotNull(message = "La fecha de actualización no puede ser nula")
    private LocalDateTime updatedAt;

    @NotBlank(message = "El cliente ID no puede estar vacío")
    @Size(max = 50, message = "El cliente ID no puede exceder 50 caracteres")
    private String customerId;

    @Size(max = 200, message = "El mensaje de error no puede exceder 200 caracteres")
    private String errorMessage;

    public String getIdempotencyKey() {
        return operationNumber + "|" + channel;
    }

    public boolean isValidForIdempotency() {
        return operationNumber != null && !operationNumber.isBlank() && 
               channel != null && !channel.isBlank();
    }

    public static LoanRequest fromOperationAndChannel(String operationNumber, String channel) {
        return LoanRequest.builder()
                .operationNumber(operationNumber)
                .channel(channel)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}