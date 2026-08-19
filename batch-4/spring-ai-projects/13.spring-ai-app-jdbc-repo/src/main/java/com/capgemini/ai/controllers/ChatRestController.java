package com.capgemini.ai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class ChatRestController {

    private final ChatClient chatClient;

    public ChatRestController(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        // Build ChatClient and configure the MessageChatMemoryAdvisor globally
        this.chatClient = chatClientBuilder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message, @RequestParam String conversationId) {
        return this.chatClient.prompt()
                .user(message)
                // Pass the conversation identifier and history window configuration
                .advisors(context -> context
                        .param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}
