package com.capgemini.ai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptTemplateRestController1 {

    private final ChatClient chatClient;

    public PromptTemplateRestController1(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Value("classpath:/promptTemplates/userPromptTemplate.st")
    Resource userPromptTemplate;
   
    @GetMapping(path = "/email-response")
    public String handleEmailResponse(@RequestParam String customerName, @RequestParam String customerEmail) {
    	return chatClient.prompt()
    					 .system("""
    		                        You are a professional customer service assistant which helps 
    		                        drafting email responses to improve the productivity of the 
    		                        customer support team
    		                     """)
    					 .user(promptTemplateSpec -> promptTemplateSpec.text(userPromptTemplate)
    							 								.param("customerName", customerName)
    							 								.param("customerMessage", customerEmail))
    					 .call()
    					 .content();
    }
}
