package com.capgemini.ai.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.ai.services.OpenAIService;

@RestController
@RequestMapping(path = "/api")
public class OpenAIController {
	private OpenAIService openAiService;
	
	//constructor injection - @Autowired is optional for constructor injection
	public OpenAIController(OpenAIService openAiService) {
		super();
		this.openAiService = openAiService;
	}

	@GetMapping("/chat")
	public String handleAskAnything(@RequestParam String question) {
		String answer = openAiService.askAnything(question);
		return answer;
	}

}
