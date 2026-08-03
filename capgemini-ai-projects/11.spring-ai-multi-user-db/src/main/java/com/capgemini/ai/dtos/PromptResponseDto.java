package com.capgemini.ai.dtos;

import java.time.Instant;

import com.capgemini.ai.entities.PromptResponse;

public record PromptResponseDto(
        Long id,
        String username,
        String prompt,
        String response,
        Instant createdAt
) {
    public static PromptResponseDto from(PromptResponse entity) {
        return new PromptResponseDto(
                entity.getId(),
                entity.getUserName(),
                entity.getPrompt(),
                entity.getResponse(),
                entity.getCreatedAt()
        );
    }
}

