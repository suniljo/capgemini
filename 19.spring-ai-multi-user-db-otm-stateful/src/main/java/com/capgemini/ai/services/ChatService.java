package com.capgemini.ai.services;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.ai.dtos.PromptResponseDto;
import com.capgemini.ai.entities.AppUser;
import com.capgemini.ai.entities.PromptResponse;
import com.capgemini.ai.repository.AppUserRepository;
import com.capgemini.ai.repository.PromptResponseRepository;

@Service
public class ChatService {

	private ChatClient chatClient;
	private PromptResponseRepository promptResponseRepository;
	private AppUserRepository userRepository;
	
	public ChatService(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, AppUserRepository userRepository, PromptResponseRepository promptResponseRepository) {
		this.chatClient = chatClientBuilder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
											.build();
		this.userRepository = userRepository;
		this.promptResponseRepository = promptResponseRepository;
	}

	@Transactional
	public PromptResponseDto askAndStore(String username, String prompt) {
		String aiResponse = chatClient.prompt()
				                      .user(prompt)
				                      .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, username))
				                      .call()
				                      .content();

	    AppUser user = userRepository.findByUsername(username)
	                				 .orElseGet(() -> userRepository.save(new AppUser(username)));
	       
	    PromptResponse entity = new PromptResponse(prompt, aiResponse);
	    
	    user.addPromptResponse(entity); // keeps both sides of the one-to-many in sync
        promptResponseRepository.save(entity);

        return PromptResponseDto.from(entity);
	}

	@Transactional(readOnly = true)
	public List<PromptResponseDto> getHistoryForUserName(String username) {
		return promptResponseRepository.findByUser_UsernameOrderByCreatedAtDesc(username)
                					   .stream()
                					   .map(PromptResponseDto::from)
                					   .toList();
	}
}
