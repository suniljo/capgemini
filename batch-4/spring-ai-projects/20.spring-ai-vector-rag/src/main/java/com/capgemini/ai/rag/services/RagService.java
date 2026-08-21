package com.capgemini.ai.rag.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RagService {

	private ChatClient chatClient;
	private VectorStore vectorStore;
	
	@Autowired
	public RagService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
		this.chatClient = chatClientBuilder.build();
		this.vectorStore = vectorStore;
	}

	public String ask(String question) {
		
		SearchRequest searchRequest = SearchRequest.builder().topK(3).query(question).similarityThreshold(0.5).build();
		
		List<Document> documents = vectorStore.similaritySearch(searchRequest);
		
		/*
		if(documents.isEmpty()) {
			return "I don't know what you are asking";
		}
		*/
		
	    String context = documents.stream()
								  .map(Document::getFormattedContent)
								  .collect(Collectors.joining("\n"));
	    
	    
	   return chatClient.prompt()
	    		  .system(""" 
	    		  	    You are a helpful AI Assistant.
				  		Answer the question using only the provided context.
				  		If the context does not contain the answer, say "I don't know"
	    		  	""")
	    		  .user(""" 	    		  		
	    		    Question:
				  	%s				  	
				  	Context:
				  	%s	    		  		
	    		   """
	    		  .formatted(question, context))
	    		  .call()
	    		  .content();
	}
}

