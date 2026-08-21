package com.capgemini.ai.configs;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.capgemini.ai.tools.TimeTools;

@Configuration
public class ChatClientConfiguration {
	
	@Bean
	public ChatClient generateChatClient(ChatClient.Builder builder, ChatMemory chatMemory, TimeTools timeTools) {
		return builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build(), new SimpleLoggerAdvisor())
					  .defaultTools(timeTools)
					  .build();
	}
}
