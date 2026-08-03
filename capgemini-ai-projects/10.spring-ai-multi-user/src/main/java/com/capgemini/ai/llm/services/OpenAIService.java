package com.capgemini.ai.llm.services;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {
	private ChatClient chatClient;
	private ChatMemory chatMemory;
	
	public OpenAIService(ChatClient.Builder builder, ChatMemory chatMemory) {
		this.chatClient = builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
								 .build();
		this.chatMemory = chatMemory;
	}

	public String askAnything(String userName, String question) {
		ChatResponse chatResponse = chatClient.prompt()
											  .user(question)
											  .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, userName))
											  .call()
											  .chatResponse();

		return chatResponse.getResult()
						   .getOutput()
						   .getText();
	}
	
	public List<String> getConversationHistory(String userName) {
		List<Message> messages = chatMemory.get(userName);
		long count = messages.stream().filter(m -> m.getMessageType() == MessageType.USER).count();
		//System.out.println("Number of Questions from customer: " + count);
		
		Stream<Message> promptMessages = messages.stream().filter(m -> m.getMessageType() == MessageType.USER); //MessageType.ASSISTANT
		List<String> prompts = promptMessages.map(m -> m.getText()).toList();
		
		return prompts;	
	}
}
