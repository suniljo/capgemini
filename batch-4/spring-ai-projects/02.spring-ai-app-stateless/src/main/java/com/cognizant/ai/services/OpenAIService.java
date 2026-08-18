package com.cognizant.ai.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {
	private ChatClient chatClient;
	
	public OpenAIService(ChatClient.Builder builder) {
		this.chatClient = builder.build();
		//System.out.println(this.chatClient.getClass().getName());
	}
	
	public String askAnything(String question) {
	     return	chatClient.prompt()
				  		  .user(question)
				  		  .call()
				  		  .content();
	}
}
