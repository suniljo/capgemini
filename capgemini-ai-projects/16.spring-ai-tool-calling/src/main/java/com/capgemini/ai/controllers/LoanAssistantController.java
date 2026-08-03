package com.capgemini.ai.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.ai.dto.ChatRequest;
import com.capgemini.ai.tools.LoanTool;

@RestController
@RequestMapping("/api/loan-assistant")
public class LoanAssistantController {
	private static final Logger log = LoggerFactory.getLogger(LoanAssistantController.class);
	
	private final ChatClient chatClient;
    private final LoanTool loanTool;

    public LoanAssistantController(ChatClient chatClient, LoanTool loanTool) {
        this.chatClient = chatClient;
        this.loanTool = loanTool;

    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request) {
    	 log.info("User asked: {}", request.message());
         return  chatClient.prompt()
                           .user(request.message())
                           .tools(loanTool)
                           .call()
                           .content();

    }
}
