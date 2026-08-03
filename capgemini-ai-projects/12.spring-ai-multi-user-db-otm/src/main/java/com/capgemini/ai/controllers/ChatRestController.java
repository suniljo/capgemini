package com.capgemini.ai.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.ai.dtos.PromptRequest;
import com.capgemini.ai.dtos.PromptResponseDto;
import com.capgemini.ai.services.ChatService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatRestController {
	@Autowired
	private ChatService chatService;

    @PostMapping
    public ResponseEntity<PromptResponseDto> askAnything(@Valid @RequestBody PromptRequest request) {
        PromptResponseDto result = chatService.askAndStore(
                						request.username(), 
                						request.prompt());
        
        return ResponseEntity.status(HttpStatus.CREATED)
        					 .body(result);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<PromptResponseDto>> getByUserName(@PathVariable String username) {
    	List<PromptResponseDto> promptResponses = chatService.getHistoryForUserName(username);
        
    	return ResponseEntity.ok(promptResponses);
    }
}
