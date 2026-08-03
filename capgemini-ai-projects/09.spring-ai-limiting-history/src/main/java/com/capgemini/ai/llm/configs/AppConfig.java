package com.capgemini.ai.llm.configs;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	ChatMemory chatMemory(ChatMemoryRepository repo) {
		return MessageWindowChatMemory.builder()
									  .chatMemoryRepository(repo)
									  .maxMessages(4) // keeps last 4 messages (USER + ASSISTANT), evicts older ones
									  .build();
	}

}
