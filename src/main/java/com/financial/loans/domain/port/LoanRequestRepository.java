package com.financial.loans.domain.port;

import java.util.Optional;
import java.util.UUID;

import com.financial.loans.domain.model.LoanRequest;

public interface LoanRequestRepository {
    LoanRequest save(LoanRequest loanRequest);

    Optional<LoanRequest> findById(UUID id);

    Optional<LoanRequest> findByIdempotencyKey(String idempotencyKey);

    LoanRequest updateStatus(UUID id, String status, String errorMessage);

    Optional<LoanRequest> findExistingRequestWithinWindow(String operationNumber, String channel);
}