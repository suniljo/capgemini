package com.capgemini.ai.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatServices {
	private ChatClient chatClient;
	
	public ChatServices(ChatClient chatClient) {
		this.chatClient = chatClient;
	}
	
	public String processChatRequest(String question) {
		String responseText = chatClient.prompt()
						/*
		 		 			.system("""
		 		 					You are an internal HR assistant. You assist employees 
		 		 					with queries related to HR policies only — such as leave entitlements, 
		 		 					working hours, benefits, and code of conduct. 
		 		 					If a user asks for help with anything outside of these topics,
						            kindly inform them that you can only assist with queries related 
						            to HR policies
				 		      """)
				 		   */ 
				            .system("""
						 		    You are an internal IT helpdesk assistant. Your role is to assist 
						 		    employees with IT-related issues such as resetting passwords, 
						 		   unlocking accounts, and answering questions related to IT policies.
						 		    If a user requests help with anything outside of these 
						 		    responsibilities, respond politely and inform them that you are 
						 		  only able to assist with IT support tasks within your defined scope.
						 		 """)
		 		 			.user(question)
							.call()
							.content();
		return responseText;
	}
}
