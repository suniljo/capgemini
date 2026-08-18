package com.capgemini.ai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class ChatRestController {
	
	private ChatClient chatClient;
	
	//dependency injection
	public ChatRestController(ChatClient.Builder builder) {
		this.chatClient = builder.build();
	}
	
	@GetMapping(path = "/chat")
	public String handleChat(@RequestParam("question") String question) {
		String responseMessage = chatClient.prompt(question)
											.call()
											.content();
		return responseMessage;
	}
}
