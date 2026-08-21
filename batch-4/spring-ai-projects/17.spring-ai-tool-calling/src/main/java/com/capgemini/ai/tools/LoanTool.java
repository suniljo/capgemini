package com.capgemini.ai.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.capgemini.ai.dto.LoanStatusResponse;
import com.capgemini.ai.services.LoanService;

@Component
public class LoanTool {

    private final LoanService loanService;
    
    private static final Logger log =
            LoggerFactory.getLogger(LoanTool.class);

    public LoanTool(LoanService loanService) {
        this.loanService = loanService;
    }

    @Tool(description = "Get current loan application status using loan application id")
    public LoanStatusResponse getLoanStatus (
            @ToolParam(description = "Loan application id") String applicationId) {

        log.info("Tool selected by model");
        log.info(" applicationId={}", applicationId);

        return loanService.getLoanStatus(applicationId);

    }
}
