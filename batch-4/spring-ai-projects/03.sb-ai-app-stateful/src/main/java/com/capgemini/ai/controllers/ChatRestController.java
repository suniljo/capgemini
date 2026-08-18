package com.capgemini.ai.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.ai.services.ChatServices;

@Controller
public class ChatRestController {
	private ChatServices services;

	public ChatRestController(ChatServices services) {
		super();
		this.services = services;
	}
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@PostMapping("/askAnything")
	public String handleAskAnything(@RequestParam String question, Model model) {

		String answer = services.askAnything(question);

		model.addAttribute("question", question);
		model.addAttribute("answer", answer);

		return "index";

	}	

}
