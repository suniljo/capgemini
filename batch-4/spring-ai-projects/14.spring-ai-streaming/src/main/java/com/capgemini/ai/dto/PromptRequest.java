package com.capgemini.ai.dto;

import jakarta.validation.constraints.NotBlank;

public record PromptRequest(
        @NotBlank(message = "prompt is required")
        String prompt) { }
