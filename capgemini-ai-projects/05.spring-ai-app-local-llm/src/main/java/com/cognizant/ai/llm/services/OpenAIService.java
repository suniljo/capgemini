package com.cognizant.ai.llm.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {
	private ChatClient chatClient;
	
	public OpenAIService(ChatClient.Builder builder, ChatMemory chatMemory) {
		this.chatClient = builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
	}
	
	public String askAnything(String question) {
	     return	chatClient.prompt()
	    		 		 // .system("You are a helpful Java and Spring Boot assistant.")
				  		  .user(question)
				  		  .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, "user-101"))
				  		  .call()
				  		  .content();
	}
}
