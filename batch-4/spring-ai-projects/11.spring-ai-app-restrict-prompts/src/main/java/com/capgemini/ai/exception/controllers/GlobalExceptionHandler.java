package com.capgemini.ai.exception.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.capgemini.ai.exception.PromptLimitExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = PromptLimitExceededException.class)
	public ResponseEntity<String> handlePromptLimitExceededException(PromptLimitExceededException ex) {
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
							 .body(ex.getMessage());
	}
}
