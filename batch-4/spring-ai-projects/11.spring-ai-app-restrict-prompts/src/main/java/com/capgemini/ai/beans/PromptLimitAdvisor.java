package com.capgemini.ai.beans;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.capgemini.ai.exception.PromptLimitExceededException;

@Component
public class PromptLimitAdvisor implements CallAdvisor {

	private final ChatMemory chatMemory;
	private final int maxUserTurns;
	
	/*
    public PromptLimitAdvisor(ChatMemory chatMemory, int maxUserTurns) {
        this.chatMemory = chatMemory;
        this.maxUserTurns = maxUserTurns;
    }
    */
	
	public PromptLimitAdvisor(ChatMemory chatMemory, @Value("${app.chat.max-user-turns}") int maxUserTurns) {
		this.chatMemory = chatMemory;
		this.maxUserTurns = maxUserTurns;
	}

	@Override
	public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
		String conversationId = (String) chatClientRequest.context()
														  .get(ChatMemory.CONVERSATION_ID);

		long userTurns = chatMemory.get(conversationId)
								   .stream()
								   .filter(m -> m.getMessageType() == MessageType.USER)
								   .count();

		if (userTurns >= maxUserTurns) {
			throw new PromptLimitExceededException("Prompt limit of " + maxUserTurns + " reached for conversation " + conversationId);
		}

		return callAdvisorChain.nextCall(chatClientRequest);
	}

	@Override
	public String getName() {
		//return "PromptLimitAdvisor";
		return this.getClass().getSimpleName();
	}

	@Override
	public int getOrder() {
		System.out.println("PromptLimitAdvisor Order: " + Ordered.HIGHEST_PRECEDENCE);
		return Ordered.HIGHEST_PRECEDENCE;
	}
}
