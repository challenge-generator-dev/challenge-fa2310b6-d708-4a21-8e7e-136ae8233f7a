package com.financial.loans.infrastructure.rest;

import com.financial.loans.application.service.LoanRequestService;
import com.financial.loans.infrastructure.rest.dto.LoanRequestDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanRequestController {

    private static final Logger logger = LoggerFactory.getLogger(LoanRequestController.class);
    private final LoanRequestService loanRequestService;

    public LoanRequestController(LoanRequestService loanRequestService) {
        this.loanRequestService = loanRequestService;
    }

    @PostMapping("/requests")
    public ResponseEntity<LoanRequestDTO> createLoanRequest(@Valid @RequestBody LoanRequestDTO requestDTO) {
        String correlationId = UUID.randomUUID().toString();
        MDC.put("correlationId", correlationId);
        MDC.put("operationNumber", requestDTO.operationNumber());
        MDC.put("channel", requestDTO.channel());
        
        try {
            logger.info("Iniciando procesamiento de solicitud de prestamo - operationNumber: {}, channel: {}", 
                    requestDTO.operationNumber(), requestDTO.channel());
            
            LoanRequestDTO result = loanRequestService.processLoanRequest(requestDTO);
            
            logger.info("Solicitud de prestamo procesada exitosamente - id: {}, status: {}", 
                    result.id(), result.status());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
            
        } catch (IllegalArgumentException e) {
            logger.warn("Solicitud rechazada por validacion de idempotencia: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            logger.error("Error al procesar solicitud de prestamo: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } finally {
            MDC.clear();
        }
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<LoanRequestDTO> getLoanRequest(@PathVariable UUID id) {
        MDC.put("correlationId", UUID.randomUUID().toString());
        MDC.put("loanRequestId", id.toString());
        
        try {
            logger.info("Consultando solicitud de prestamo - id: {}", id);
            
            return loanRequestService.findById(id)
                    .map(dto -> {
                        logger.info("Solicitud encontrada - id: {}, status: {}", dto.id(), dto.status());
                        return ResponseEntity.ok(dto);
                    })
                    .orElseGet(() -> {
                        logger.warn("Solicitud no encontrada - id: {}", id);
                        return ResponseEntity.notFound().build();
                    });
                    
        } catch (Exception e) {
            logger.error("Error al consultar solicitud: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } finally {
            MDC.clear();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        logger.debug("Health check solicitado");
        return ResponseEntity.ok(Map.of("status", "UP", "service", "loans-api"));
    }
}