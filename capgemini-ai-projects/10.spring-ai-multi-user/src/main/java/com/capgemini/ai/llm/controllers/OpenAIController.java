package com.capgemini.ai.llm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.ai.llm.services.OpenAIService;

@RestController
public class OpenAIController {
	@Autowired
	private OpenAIService openAiService;

	@GetMapping("/askAnything")
	public String handleAskAnything(@RequestParam("username") String userName, @RequestParam String question) {

		String answer = openAiService.askAnything(userName, question);

		return answer;
	}

	@GetMapping("/history/prompts/{userName}")
	public List<String> getPromptHistryBasedOnUserNames(@PathVariable String userName) {

		List<String> userPrompts = openAiService.getConversationHistory(userName);

		return userPrompts;
	}	
}
