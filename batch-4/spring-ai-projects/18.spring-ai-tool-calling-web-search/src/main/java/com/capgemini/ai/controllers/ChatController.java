package com.capgemini.ai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.ai.dto.ChatRequest;
import com.capgemini.ai.dto.ChatResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * Sends the prompt to OpenAI. The model decides on its own whether to call
     * the webSearch tool (registered as a default tool on the ChatClient bean)
     * before producing its final answer - no special handling needed here.
     */
    @PostMapping
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        String content = chatClient.prompt()
                				   .user(request.prompt())
                				   .call()
                				   .content();

        return ResponseEntity.ok(new ChatResponse(content));
    }
}
