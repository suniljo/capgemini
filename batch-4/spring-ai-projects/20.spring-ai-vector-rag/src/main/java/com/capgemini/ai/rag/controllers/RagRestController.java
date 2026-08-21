package com.capgemini.ai.rag.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.ai.rag.services.RagService;

@RestController
public class RagRestController {

	@Autowired
	private RagService ragService;

	@GetMapping("/rag")
	public String ask(@RequestParam String question) {
		return ragService.ask(question);
	}

}