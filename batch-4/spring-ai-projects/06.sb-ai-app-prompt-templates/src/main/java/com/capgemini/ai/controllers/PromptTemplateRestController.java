package com.capgemini.ai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptTemplateRestController {

    private final ChatClient chatClient;

    public PromptTemplateRestController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    String promptTemplateText = """
    	    A customer named {customerName} sent the following message:
    	               "{customerMessage}"

    		Write a polite and helpful email response addressing the issue.
    		Maintain a professional tone and provide reassurance.

    		Respond as if you're writing the email body only. 
    		Don't include subject. 
    	   """;
   
    @GetMapping(path = "/email")
    public String handleEmailResponse(@RequestParam String customerName, @RequestParam String customerEmail) {
    	return chatClient.prompt()
    					 .system("""
    		                        You are a professional customer service assistant which helps 
    		                        drafting email responses to improve the productivity of the 
    		                        customer support team
    		                     """)
    					 .user(promptTemplateSpec -> promptTemplateSpec.text(promptTemplateText)
    							 								.param("customerName", customerName)
    							 								.param("customerMessage", customerEmail))
    					 .call()
    					 .content();
    }
}
