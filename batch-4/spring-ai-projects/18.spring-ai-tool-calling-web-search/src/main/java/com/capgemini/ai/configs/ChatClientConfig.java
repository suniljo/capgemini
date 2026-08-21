package com.capgemini.ai.configs;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.capgemini.ai.tools.WebSearchTool;

@Configuration
public class ChatClientConfig {
	
    private static final String SYSTEM_PROMPT = """
            You are a helpful assistant. You have access to a web search tool.
            Use it whenever a question depends on current, time-sensitive, or
            fast-changing information (news, prices, releases, current holders
            of a role, "latest" anything) or anything you're not confident about
            from your own knowledge. Cite the URLs you used when you rely on
            search results.""";
    
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, WebSearchTool webSearchTool) {
    	System.out.println(webSearchTool);
        return builder
                .defaultSystem(SYSTEM_PROMPT)
                .defaultTools(webSearchTool)
                .build();
        
    }
}
