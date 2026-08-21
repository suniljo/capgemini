package com.capgemini.ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class StreamingChatService {

    private final ChatClient chatClient;

    public Flux<String> streamResponse(String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .stream()
                .content()
                .doOnSubscribe(sub -> log.debug("Starting stream for prompt: {}", prompt))
                .doOnNext(chunk -> {
                	log.info("Chunk: {}", chunk);
                	System.out.println("Next Chunk = " + chunk);
                })
                .doOnError(err -> log.error("Streaming error", err))
                .doOnComplete(() -> {
                	log.info("Stream complete");
                	System.out.println("Stream completed");
                });
    }
}
