package com.capgemini.ai.services;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.ai.dtos.PromptResponseDto;
import com.capgemini.ai.entities.PromptResponse;
import com.capgemini.ai.repository.PromptResponseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PromptResponseService {

    private final ChatClient chatClient;
    private final PromptResponseRepository repository;
    
    @Transactional
    public PromptResponseDto askAndStore(String username, String prompt) {
        String aiResponse = chatClient.prompt()
                					  .user(prompt)
                					  .call()
                					  .content();

        PromptResponse entity = PromptResponse.builder()
                							  .userName(username)
                							  .prompt(prompt)
                							  .response(aiResponse)
                							  .build();

        PromptResponse saved = repository.save(entity);
        
        return PromptResponseDto.from(saved);
    }

    @Transactional(readOnly = true)
    public List<PromptResponseDto> getHistoryForUserName(String username) {
        return repository.findByUserNameOrderByCreatedAtDesc(username)
                		 .stream()
                		 .map(PromptResponseDto::from)
                		 .toList();
    }
}
