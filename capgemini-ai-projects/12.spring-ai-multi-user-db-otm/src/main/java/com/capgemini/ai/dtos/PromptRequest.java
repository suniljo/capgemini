package com.capgemini.ai.dtos;

import jakarta.validation.constraints.NotBlank;

public record PromptRequest(
        @NotBlank(message = "username is required")
        String username,

        @NotBlank(message = "prompt is required")
        String prompt) { }
