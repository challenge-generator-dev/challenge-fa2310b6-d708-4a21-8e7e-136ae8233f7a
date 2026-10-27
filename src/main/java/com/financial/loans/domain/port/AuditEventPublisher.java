package com.financial.loans.domain.port;

import com.financial.loans.domain.model.LoanRequest;

public interface AuditEventPublisher {
    void publishLoanAcceptedEvent(LoanRequest loanRequest);

    void publishLoanRejectedEvent(LoanRequest loanRequest, String rejectionReason);
}