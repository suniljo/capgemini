package com.capgemini.ai.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.ai.services.ChatServices;

@RestController
@RequestMapping(path = "/api")
public class ChatRestController {
	
	private ChatServices chatServices;	
	
	public ChatRestController(ChatServices chatServices) {
		super();
		this.chatServices = chatServices;
	}


	@GetMapping(path = "/chat")
	public String handleChatRequest(@RequestParam String question) {
		String responseText = chatServices.processChatRequest(question);
		return responseText;
	}
}
