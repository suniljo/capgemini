package com.capgemini.ai.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.capgemini.ai.dto.LoanStatusResponse;

@Service
public class LoanService {

    private final Map<String, LoanStatusResponse> loanDatabase = Map.of(
            "APP123", new LoanStatusResponse(
                    "APP123",
                    "UNDER_REVIEW",
                    "Your loan application is currently under review by the credit team."
            ),
            "APP456", new LoanStatusResponse(
                    "APP456",
                    "APPROVED",
                    "Your loan application has been approved."
            )
    );

    public LoanStatusResponse getLoanStatus(String applicationId) {

        return loanDatabase.getOrDefault(
                applicationId,
                new LoanStatusResponse(
                        applicationId,
                        "NOT_FOUND",
                        "No loan application was found for this application id."
                )
        );
    }
}
