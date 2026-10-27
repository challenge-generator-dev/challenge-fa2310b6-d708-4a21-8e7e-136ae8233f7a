package com.financial.loans.infrastructure.adapter;

import com.financial.loans.domain.model.LoanRequest;
import com.financial.loans.domain.port.LoanRequestRepository;
import com.financial.loans.infrastructure.persistence.entity.LoanRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class LoanRequestJpaRepository implements LoanRequestRepository {

    private final LoanRequestJpaAdapter jpaAdapter;

    @Autowired
    public LoanRequestJpaRepository(LoanRequestJpaAdapter jpaAdapter) {
        this.jpaAdapter = jpaAdapter;
    }

    @Override
    public LoanRequest save(LoanRequest loanRequest) {
        LoanRequestEntity entity = toEntity(loanRequest);
        LoanRequestEntity savedEntity = jpaAdapter.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoanRequest> findById(UUID id) {
        return jpaAdapter.findById(id).map(this::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoanRequest> findByIdempotencyKey(String idempotencyKey) {
        return jpaAdapter.findByIdempotencyKey(idempotencyKey).map(this::toDomain);
    }

    @Override
    public LoanRequest updateStatus(UUID id, String status, String errorMessage) {
        Optional<LoanRequestEntity> existingEntity = jpaAdapter.findById(id);
        if (existingEntity.isEmpty()) {
            throw new IllegalArgumentException("Loan request not found with id: " + id);
        }
        
        LoanRequestEntity entity = existingEntity.get();
        entity.setStatus(status);
        entity.setErrorMessage(errorMessage);
        entity.setUpdatedAt(LocalDateTime.now());
        
        LoanRequestEntity updatedEntity = jpaAdapter.save(entity);
        return toDomain(updatedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoanRequest> findExistingRequestWithinWindow(String operationNumber, String channel) {
        LocalDateTime windowStart = LocalDateTime.now().minusHours(24);
        return jpaAdapter.findByOperationNumberAndChannelAndCreatedAtAfter(operationNumber, channel, windowStart)
                .map(this::toDomain);
    }

    private LoanRequestEntity toEntity(LoanRequest loanRequest) {
        LoanRequestEntity entity = new LoanRequestEntity();
        entity.setId(loanRequest.getId());
        entity.setOperationNumber(loanRequest.getOperationNumber());
        entity.setChannel(loanRequest.getChannel());
        entity.setAmount(loanRequest.getAmount());
        entity.setStatus(loanRequest.getStatus());
        entity.setCreatedAt(loanRequest.getCreatedAt());
        entity.setUpdatedAt(loanRequest.getUpdatedAt());
        entity.setCustomerId(loanRequest.getCustomerId());
        entity.setErrorMessage(loanRequest.getErrorMessage());
        
        String idempotencyKey = buildIdempotencyKey(loanRequest.getOperationNumber(), loanRequest.getChannel());
        entity.setIdempotencyKey(idempotencyKey);
        
        return entity;
    }

    private LoanRequest toDomain(LoanRequestEntity entity) {
        LoanRequest loanRequest = LoanRequest.fromOperationAndChannel(
            entity.getOperationNumber(), 
            entity.getChannel()
        );
        
        try {
            var idField = LoanRequest.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(loanRequest, entity.getId());
            
            var amountField = LoanRequest.class.getDeclaredField("amount");
            amountField.setAccessible(true);
            amountField.set(loanRequest, entity.getAmount());
            
            var statusField = LoanRequest.class.getDeclaredField("status");
            statusField.setAccessible(true);
            statusField.set(loanRequest, entity.getStatus());
            
            var createdAtField = LoanRequest.class.getDeclaredField("createdAt");
            createdAtField.setAccessible(true);
            createdAtField.set(loanRequest, entity.getCreatedAt());
            
            var updatedAtField = LoanRequest.class.getDeclaredField("updatedAt");
            updatedAtField.setAccessible(true);
            updatedAtField.set(loanRequest, entity.getUpdatedAt());
            
            var customerIdField = LoanRequest.class.getDeclaredField("customerId");
            customerIdField.setAccessible(true);
            customerIdField.set(loanRequest, entity.getCustomerId());
            
            var errorMessageField = LoanRequest.class.getDeclaredField("errorMessage");
            errorMessageField.setAccessible(true);
            errorMessageField.set(loanRequest, entity.getErrorMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error mapping entity to domain", e);
        }
        
        return loanRequest;
    }

    private String buildIdempotencyKey(String operationNumber, String channel) {
        return operationNumber + ":" + channel;
    }
}