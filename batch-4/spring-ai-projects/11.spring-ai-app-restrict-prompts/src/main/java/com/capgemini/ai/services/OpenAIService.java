package com.capgemini.ai.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

import com.capgemini.ai.beans.PromptLimitAdvisor;

@Service
public class OpenAIService {
	private ChatClient chatClient;
	
	/*
	// stateful client
	public OpenAIService(ChatClient.Builder builder, ChatMemory chatMemory) {
		this.chatClient = builder.defaultAdvisors(
		        new PromptLimitAdvisor(chatMemory, 2),   // e.g., cap at 2 user turns
		        MessageChatMemoryAdvisor.builder(chatMemory).build()
		    )
		    .build();
	}
	*/
	
	public OpenAIService(ChatClient.Builder builder, ChatMemory chatMemory, PromptLimitAdvisor promptLimitAdvisor) {
		this.chatClient = builder.defaultAdvisors(promptLimitAdvisor, 
				MessageChatMemoryAdvisor.builder(chatMemory).build())
				.build();
	}
	
	public String askAnything(String question) {
	     return	chatClient.prompt()
				  		  .user(question)				  		  
				  		  .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, "user-101"))
				  		  .call()
				  		  .content();
	}
}
