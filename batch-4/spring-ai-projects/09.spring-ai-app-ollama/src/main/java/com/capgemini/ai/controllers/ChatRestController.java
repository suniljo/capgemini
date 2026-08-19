package com.capgemini.ai.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.ai.services.ChatServices;

@RestController
@RequestMapping(path = "/api")
public class ChatRestController {

	public ChatServices services;

	public ChatRestController(ChatServices services) {
		super();
		this.services = services;
	}
	

	@GetMapping("/askAnything")
	public String handleAskAnything(@RequestParam String question) {

		String answer = services.askAnything(question);

		return answer;
	}	
}
