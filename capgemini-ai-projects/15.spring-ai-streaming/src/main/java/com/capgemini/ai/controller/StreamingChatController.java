package com.capgemini.ai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.ai.dto.PromptRequest;
import com.capgemini.ai.service.StreamingChatService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class StreamingChatController {

    private final StreamingChatService streamingChatService;

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamGet(@RequestParam String prompt) {
    	Flux<String> fluxResponse = streamingChatService.streamResponse(prompt);
        return toSse(fluxResponse);
    }


    @PostMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamPost(@RequestBody PromptRequest request) {
    	String prompt = request.prompt();
    	Flux<String> fluxResponse = streamingChatService.streamResponse(prompt);
        return toSse(fluxResponse);
    }

    private Flux<ServerSentEvent<String>> toSse(Flux<String> chunks) {
    	Flux<ServerSentEvent<String>> data = chunks
                								.map(chunk -> ServerSentEvent.builder(chunk)
                								.event("message")
                        					    .build());

        // Emit a distinct terminal event so the client knows the stream is done
        // (some proxies/clients don't reliably surface Flux completion otherwise).
        Flux<ServerSentEvent<String>> completion = Flux.just(
                ServerSentEvent.<String>builder("")
                        	.event("complete")
                        	.build());
        
        return data.concatWith(completion);
    }
}
