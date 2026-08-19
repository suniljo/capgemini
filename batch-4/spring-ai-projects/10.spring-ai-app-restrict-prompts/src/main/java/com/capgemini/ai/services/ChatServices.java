package com.capgemini.ai.services;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;


@Service
public class ChatServices {
	private ChatClient chatClient;
	private ChatMemory chatMemory;
	
	// stateful client
	public ChatServices(ChatClient.Builder builder, ChatMemory chatMemory) {
		this.chatClient = builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
		this.chatMemory = chatMemory;
	}

	public String processQuestion(String question) {
		List<Message> messages = chatMemory.get("user-101");
		//System.out.println("no of messages = " + messages.size());
		long countOfUserMessages = messages.stream()
										   .filter(message -> message.getMessageType() == MessageType.USER)
										   .count();
		System.out.println("Number of Questions from customer: " + countOfUserMessages);

		if(countOfUserMessages >= 2) {
			 throw new IllegalArgumentException("Too many prompts from the User. Please clear the memory or start a new conversation.");
		}
		
        
		ChatResponse chatResponse = chatClient.prompt()
		  		  							  .user(question)
		  		  							  .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, "user-101"))
		  		  							  .call()
		  		  							  .chatResponse();
		
		
     	// Access token metadata
        Usage usage = chatResponse.getMetadata().getUsage();
        
        Integer promptTokens = usage.getPromptTokens(); // Input tokens sent to the LLM
        Integer completionTokens = usage.getCompletionTokens();  // Output tokens returned
        Integer totalTokens = usage.getTotalTokens(); // Combined total
        
        System.out.println("Prompt Tokens: " + promptTokens);
        System.out.println("Completion Tokens: " + completionTokens);
        System.out.println("Total Tokens: " + totalTokens);
        
        
		String responseText = chatResponse.getResult()
										  .getOutput()
										  .getText();
		
		return responseText;
	}
}
