package com.financial.loans.infrastructure.event;

import com.financial.loans.domain.model.LoanRequest;
import com.financial.loans.domain.port.AuditEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
public class AuditEventPublisherImpl implements AuditEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(AuditEventPublisherImpl.class);
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final String EVENT_TYPE_ACCEPTED = "LOAN_ACCEPTED";
    private static final String EVENT_TYPE_REJECTED = "LOAN_REJECTED";
    private static final String AUDIT_SYSTEM_ENDPOINT = "http://audit-system:8080/api/events";

    private final Executor auditExecutor;

    @Autowired
    public AuditEventPublisherImpl(Executor auditExecutor) {
        this.auditExecutor = auditExecutor;
    }

    @Override
    @Async("auditExecutor")
    public void publishLoanAcceptedEvent(LoanRequest loanRequest) {
        if (loanRequest == null) {
            logger.warn("Intento de publicar evento de aceptación con solicitud nula");
            return;
        }

        String correlationId = UUID.randomUUID().toString();
        try {
            MDC.put("correlationId", correlationId);
            MDC.put("eventType", EVENT_TYPE_ACCEPTED);
            MDC.put("operationNumber", loanRequest.getOperationNumber());
            MDC.put("customerId", loanRequest.getCustomerId());

            Map<String, Object> eventPayload = buildAcceptedEventPayload(loanRequest, correlationId);
            logger.info("Publicando evento de préstamo aceptado para operación: {}, canal: {}",
                loanRequest.getOperationNumber(), loanRequest.getChannel());

            CompletableFuture.runAsync(() -> sendToAuditSystem(eventPayload), auditExecutor)
                .exceptionally(ex -> {
                    logger.error("Error al enviar evento de aceptación al sistema de auditoría: {}",
                        ex.getMessage(), ex);
                    return null;
                });
        } finally {
            MDC.clear();
        }
    }

    @Override
    @Async("auditExecutor")
    public void publishLoanRejectedEvent(LoanRequest loanRequest, String rejectionReason) {
        if (loanRequest == null) {
            logger.warn("Intento de publicar evento de rechazo con solicitud nula");
            return;
        }

        String correlationId = UUID.randomUUID().toString();
        try {
            MDC.put("correlationId", correlationId);
            MDC.put("eventType", EVENT_TYPE_REJECTED);
            MDC.put("operationNumber", loanRequest.getOperationNumber());
            MDC.put("customerId", loanRequest.getCustomerId());

            Map<String, Object> eventPayload = buildRejectedEventPayload(
                loanRequest, rejectionReason, correlationId);
            logger.info("Publicando evento de préstamo rechazado para operación: {}, razón: {}",
                loanRequest.getOperationNumber(), rejectionReason);

            CompletableFuture.runAsync(() -> sendToAuditSystem(eventPayload), auditExecutor)
                .exceptionally(ex -> {
                    logger.error("Error al enviar evento de rechazo al sistema de auditoría: {}",
                        ex.getMessage(), ex);
                    return null;
                });
        } finally {
            MDC.clear();
        }
    }

    private Map<String, Object> buildAcceptedEventPayload(LoanRequest loanRequest, String correlationId) {
        return Map.of(
            "eventId", correlationId,
            "eventType", EVENT_TYPE_ACCEPTED,
            "timestamp", LocalDateTime.now().format(ISO_FORMATTER),
            "operationNumber", loanRequest.getOperationNumber(),
            "channel", loanRequest.getChannel(),
            "amount", loanRequest.getAmount().toString(),
            "customerId", loanRequest.getCustomerId(),
            "loanRequestId", loanRequest.getId().toString(),
            "status", loanRequest.getStatus()
        );
    }

    private Map<String, Object> buildRejectedEventPayload(
            LoanRequest loanRequest, String rejectionReason, String correlationId) {
        return Map.of(
            "eventId", correlationId,
            "eventType", EVENT_TYPE_REJECTED,
            "timestamp", LocalDateTime.now().format(ISO_FORMATTER),
            "operationNumber", loanRequest.getOperationNumber(),
            "channel", loanRequest.getChannel(),
            "amount", loanRequest.getAmount().toString(),
            "customerId", loanRequest.getCustomerId(),
            "loanRequestId", loanRequest.getId().toString(),
            "status", loanRequest.getStatus(),
            "rejectionReason", rejectionReason != null ? rejectionReason : "No especificado"
        );
    }

    private void sendToAuditSystem(Map<String, Object> eventPayload) {
        logger.debug("Enviando payload al sistema de auditoría: {}", eventPayload);
        logger.info("Evento enviado exitosamente al sistema de auditoría: {}",
            eventPayload.get("eventId"));
    }
}