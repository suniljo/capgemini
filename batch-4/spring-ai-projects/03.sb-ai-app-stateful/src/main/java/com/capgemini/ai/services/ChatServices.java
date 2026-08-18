package com.capgemini.ai.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

import com.openai.models.ChatModel;

@Service
public class ChatServices {
	private ChatClient chatClient;

	public ChatServices(ChatClient.Builder builder, ChatMemory chatMemory) {
		this.chatClient = builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
	}
	
	public String askAnything(String question) {
		return chatClient.prompt()
						 .user(question)
						 .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, "user-101"))
						 .call()
						 .content();
	}
	
}
