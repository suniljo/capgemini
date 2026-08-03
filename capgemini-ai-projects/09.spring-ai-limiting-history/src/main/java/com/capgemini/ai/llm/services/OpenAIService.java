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

	public String askAnything(String question) {
		ChatResponse chatResponse = chatClient.prompt()
											  .user(question)
											  .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, "user-101"))
											  .call()
											  .chatResponse();

		List<Message> messages = chatMemory.get("user-101");
		long count = messages.stream().filter(m -> m.getMessageType() == MessageType.USER).count();
		System.out.println("Number of Questions from customer: " + count);
		
		Stream<Message> promptMessages = messages.stream().filter(m -> m.getMessageType() == MessageType.USER);
		promptMessages.forEach(m -> System.out.println("Question: " + m.getText()));
		
		return chatResponse.getResult()
						   .getOutput()
						   .getText();
	}
}
