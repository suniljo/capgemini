package com.cognizant.ai.services;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {
	private ChatClient chatClient;
	
	//-- making chatclient as statefull
	public OpenAIService(ChatClient.Builder builder, ChatMemory chatMemory) {
		this.chatClient = builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
	}
	
	public String askAnything(String question) {
	     return	chatClient.prompt()
				  		  .user(question)
				  		  .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, "user-101"))
				  		  .call()
				  		  .content();
	}
	
    public String getTravelGuidance(String place, String month, String language, String budget) {
        
        PromptTemplate promptTemplate = new PromptTemplate("Welcome to the {place} travel guide!\n"
                + "If you're visiting in {month}, here's what you can do:\n" + "1. Must-visit attractions.\n"
                + "2. Local cuisine you must try.\n" + "3. Useful phrases in {language}.\n"
                + "4. Tips for traveling on a {budget} budget.\n" + "Enjoy your trip!");
        
        Prompt prompt = promptTemplate.create(Map.of("place", place, "month", month, "language", language, "budget", budget));
        
        return chatClient.prompt(prompt)
        				 .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, "user-102"))
        				 .call()
        				 .chatResponse()
        				 .getResult()
        				 .getOutput()
        				 .getText();
    }
	
}
