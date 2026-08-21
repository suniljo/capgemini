package com.capgemini.ai.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/rag")
public class RAGRestController {

	private final ChatClient chatClient;
	private final VectorStore vectorStore;
	
    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource promptTemplate;	
    
	public RAGRestController(ChatClient chatClient, VectorStore vectorStore) {
		super();
		this.chatClient = chatClient;
		this.vectorStore = vectorStore;
	}
	
	@GetMapping(path = "query")
	public ResponseEntity<String> handleQueryFromCustomer(@RequestHeader("username") String username,
            @RequestParam("question") String question){
		
		SearchRequest searchRequest = SearchRequest.builder()
												  .query(question)
												  .topK(30)
												  .similarityThreshold(0.5)
												  .build();
		
		List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
		
		String similarContext = similarDocs.stream()
										   .map(Document::getText)
										   .collect(Collectors.joining(System.lineSeparator()));
		
		String responseText = chatClient.prompt()
										.system(promptSystemSpec -> promptSystemSpec.text(promptTemplate)
						                        .param("documents", similarContext))
										.advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, username))
										.user(question)
										.call()
										.content();
		
		return ResponseEntity.ok(responseText);
	}
	
}
