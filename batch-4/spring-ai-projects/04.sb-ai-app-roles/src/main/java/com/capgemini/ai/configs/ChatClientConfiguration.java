package com.capgemini.ai.configs;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {
	
	@Bean
	public ChatClient getnerateChatClient(ChatClient.Builder builder) {
		return builder.defaultSystem("""
		 		 					You are an internal HR assistant. You assist employees 
		 		 					with queries related to HR policies only — such as leave entitlements, 
		 		 					working hours, benefits, and code of conduct. 
		 		 					If a user asks for help with anything outside of these topics,
						            kindly inform them that you can only assist with queries related 
						            to HR policies. Provide proper addressing
				 		      """)
								.defaultUser("How can you help me?")
								.build();
	}
}
