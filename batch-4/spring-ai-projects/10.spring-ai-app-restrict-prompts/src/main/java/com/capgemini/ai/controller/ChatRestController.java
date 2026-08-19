package com.capgemini.ai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.ai.services.ChatServices;

@RestController
@RequestMapping(path = "/api")
public class ChatRestController {

	private ChatServices chatService;

	public ChatRestController(ChatServices chatService) {
		super();
		this.chatService = chatService;
	}
	
	@GetMapping(path = "/chat")
	public String askAnything(@RequestParam String question) {
		return chatService.processQuestion(question);
	}
}
