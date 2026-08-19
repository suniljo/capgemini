package com.capgemini.ai.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatServices {
	private ChatClient chatClient;
	
	public ChatServices(ChatClient chatClient) {
		this.chatClient = chatClient;
	}
	
	public String processChatRequest(String question) {
		String responseText = chatClient.prompt()										
						    			.user(question)
						    			.call()
						    			.content();
		return responseText;
	}
}
