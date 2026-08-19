package com.capgemini.ai.configs;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ChatMemoryConfiguration {

	@Bean
	public ChatMemory chatMemoryRepository(JdbcTemplate jdbcTemplate) {
        // Spring AI 2.0 idiomatic builder instantiation
        JdbcChatMemoryRepository repository = JdbcChatMemoryRepository.builder()
                											.jdbcTemplate(jdbcTemplate)
                											.build();
   
        // Retain a rolling window of the last 10 messages for the LLM context
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(repository)
                .maxMessages(10)
                .build();
	}
}
