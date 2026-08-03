package com.cognizant.ai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cognizant.ai.services.OpenAIService;

@Controller
public class OpenAIController {
	@Autowired
	private OpenAIService openAiService;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@PostMapping("/askAnything")
	public String handleAskAnything(@RequestParam String question, Model model) {

		String answer = openAiService.askAnything(question);

		model.addAttribute("question", question);
		model.addAttribute("answer", answer);

		return "index";
	}

}
