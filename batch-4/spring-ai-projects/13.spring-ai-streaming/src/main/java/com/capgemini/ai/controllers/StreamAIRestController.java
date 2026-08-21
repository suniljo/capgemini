package com.capgemini.ai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class StreamAIRestController {
    private final ChatClient chatClient;

    public StreamAIRestController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return chatClient.prompt()                
                		 .user(message)
                		 .call()
                		 .content();
    }
    
    //reactive programming  
    @GetMapping("/stream")
    public Flux<String> askAnythingStream(@RequestParam("message") String message) {
        return chatClient.prompt()
        				 .user(message)
        				 .stream()
        				 .content();
    }
}
