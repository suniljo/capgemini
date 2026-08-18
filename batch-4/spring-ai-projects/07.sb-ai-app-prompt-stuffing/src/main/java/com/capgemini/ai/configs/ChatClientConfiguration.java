package com.capgemini.ai.configs;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {
	
	@Bean
	public ChatClient getnerateChatClient(ChatClient.Builder builder) {
		return builder.build();
	}
}
