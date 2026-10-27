package com.financial.loans.application.service;

import com.financial.loans.domain.model.LoanRequest;
import com.financial.loans.domain.port.AuditEventPublisher;
import com.financial.loans.domain.port.LoanRequestRepository;
import com.financial.loans.infrastructure.rest.dto.LoanRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanRequestService {

    private static final Logger logger = LoggerFactory.getLogger(LoanRequestService.class);
    private static final String STATUS_PENDING = "PENDING";
    private static final String STATUS_APPROVED = "APPROVED";
    private static final String STATUS_REJECTED = "REJECTED";
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("100000.00");
    private static final BigDecimal MIN_AMOUNT = new BigDecimal("100.00");

    private final LoanRequestRepository loanRequestRepository;
    private final AuditEventPublisher auditEventPublisher;

    @Autowired
    public LoanRequestService(LoanRequestRepository loanRequestRepository,
                              AuditEventPublisher auditEventPublisher) {
        this.loanRequestRepository = loanRequestRepository;
        this.auditEventPublisher = auditEventPublisher;
    }

    @Transactional
    public LoanRequest processLoanRequest(LoanRequestDTO dto) {
        String idempotencyKey = dto.buildIdempotencyKey();
        
        Map<String, String> context = new HashMap<>();
        context.put("operationNumber", dto.operationNumber());
        context.put("channel", dto.channel());
        context.put("idempotencyKey", idempotencyKey);
        MDC.clear();
        MDC.setContextMap(context);
        
        try {
            logger.info("Processing loan request - operation: {}, channel: {}, amount: {}",
                dto.operationNumber(), dto.channel(), dto.amount());
            
            Optional<LoanRequest> existingRequest = 
                loanRequestRepository.findByIdempotencyKey(idempotencyKey);
            
            if (existingRequest.isPresent()) {
                LoanRequest existing = existingRequest.get();
                logger.info("Returning existing request with status: {} for idempotency key: {}",
                    existing.getStatus(), idempotencyKey);
                return existing;
            }
            
            Optional<LoanRequest> recentRequest = 
                loanRequestRepository.findExistingRequestWithinWindow(
                    dto.operationNumber(), dto.channel());
            
            if (recentRequest.isPresent()) {
                logger.warn("Duplicate request detected within 24h window - operation: {}, channel: {}",
                    dto.operationNumber(), dto.channel());
                return recentRequest.get();
            }
            
            validateRequest(dto);
            
            LoanRequest loanRequest = createLoanRequest(dto);
            
            boolean approved = evaluateCreditRisk(dto);
            
            if (approved) {
                loanRequest = updateLoanRequestStatus(loanRequest, STATUS_APPROVED, null);
                auditEventPublisher.publishLoanAcceptedEvent(loanRequest);
                logger.info("Loan request APPROVED - id: {}, amount: {}", 
                    loanRequest.getId(), loanRequest.getAmount());
            } else {
                String rejectionReason = "Credit risk evaluation failed - insufficient credit score";
                loanRequest = updateLoanRequestStatus(loanRequest, STATUS_REJECTED, rejectionReason);
                auditEventPublisher.publishLoanRejectedEvent(loanRequest, rejectionReason);
                logger.info("Loan request REJECTED - id: {}, reason: {}", 
                    loanRequest.getId(), rejectionReason);
            }
            
            return loanRequest;
            
        } catch (Exception e) {
            logger.error("Error processing loan request - operation: {}, channel: {}",
                dto.operationNumber(), dto.channel(), e);
            throw e;
        } finally {
            MDC.clear();
        }
    }

    @Transactional(readOnly = true)
    public Optional<LoanRequest> getLoanRequest(UUID id) {
        logger.debug("Retrieving loan request with id: {}", id);
        return loanRequestRepository.findById(id);
    }

    private void validateRequest(LoanRequestDTO dto) {
        if (dto.amount().compareTo(MIN_AMOUNT) < 0) {
            throw new IllegalArgumentException(
                "El monto mínimo permitido es: " + MIN_AMOUNT);
        }
        if (dto.amount().compareTo(MAX_AMOUNT) > 0) {
            throw new IllegalArgumentException(
                "El monto máximo permitido es: " + MAX_AMOUNT);
        }
        
        if (dto.operationNumber() == null || dto.operationNumber().isBlank()) {
            throw new IllegalArgumentException("El número de operación no puede estar vacío");
        }
        
        if (dto.channel() == null || dto.channel().isBlank()) {
            throw new IllegalArgumentException("El canal no puede estar vacío");
        }
        
        logger.debug("Request validation passed for operation: {}", dto.operationNumber());
    }

    private LoanRequest createLoanRequest(LoanRequestDTO dto) {
        LoanRequest loanRequest = LoanRequest.fromOperationAndChannel(
            dto.operationNumber(), 
            dto.channel()
        );
        
        try {
            var idField = LoanRequest.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(loanRequest, UUID.randomUUID());
            
            var amountField = LoanRequest.class.getDeclaredField("amount");
            amountField.setAccessible(true);
            amountField.set(loanRequest, dto.amount());
            
            var statusField = LoanRequest.class.getDeclaredField("status");
            statusField.setAccessible(true);
            statusField.set(loanRequest, STATUS_PENDING);
            
            var createdAtField = LoanRequest.class.getDeclaredField("createdAt");
            createdAtField.setAccessible(true);
            createdAtField.set(loanRequest, LocalDateTime.now());
            
            var updatedAtField = LoanRequest.class.getDeclaredField("updatedAt");
            updatedAtField.setAccessible(true);
            updatedAtField.set(loanRequest, LocalDateTime.now());
            
            var customerIdField = LoanRequest.class.getDeclaredField("customerId");
            customerIdField.setAccessible(true);
            customerIdField.set(loanRequest, dto.customerId());
            
            var errorMessageField = LoanRequest.class.getDeclaredField("errorMessage");
            errorMessageField.setAccessible(true);
            errorMessageField.set(loanRequest, null);
            
        } catch (Exception e) {
            throw new RuntimeException("Error creating loan request", e);
        }
        
        return loanRequestRepository.save(loanRequest);
    }

    private LoanRequest updateLoanRequestStatus(LoanRequest loanRequest, String status, String errorMessage) {
        return loanRequestRepository.updateStatus(loanRequest.getId(), status, errorMessage);
    }

    private boolean evaluateCreditRisk(LoanRequestDTO dto) {
        logger.debug("Evaluating credit risk for customer: {}, amount: {}", 
            dto.customerId(), dto.amount());
        
        BigDecimal riskScore = calculateRiskScore(dto);
        boolean approved = riskScore.compareTo(new BigDecimal("500")) >= 0;
        
        logger.debug("Risk score: {}, approved: {}", riskScore, approved);
        return approved;
    }

    private BigDecimal calculateRiskScore(LoanRequestDTO dto) {
        BigDecimal baseScore = new BigDecimal("600");
        
        if (dto.amount().compareTo(new BigDecimal("10000")) > 0) {
            baseScore = baseScore.subtract(new BigDecimal("100"));
        }
        
        String channel = dto.channel().toUpperCase();
        if ("ONLINE".equals(channel) || "MOBILE".equals(channel)) {
            baseScore = baseScore.add(new BigDecimal("50"));
        }
        
        return baseScore;
    }
}