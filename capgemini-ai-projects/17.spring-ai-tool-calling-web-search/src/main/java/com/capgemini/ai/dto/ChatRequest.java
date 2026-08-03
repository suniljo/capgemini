package com.capgemini.ai.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(
		@NotBlank(message = "prompt is required") String prompt) {
}
