package com.cognizant.ai.llm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.ai.llm.services.OpenAIService;

@RestController
public class OpenAIController {
	@Autowired
	private OpenAIService openAiService;

	@GetMapping("/askAnything")
	public String handleAskAnything(@RequestParam String question) {

		String answer = openAiService.askAnything(question);

		return answer;
	}

}
