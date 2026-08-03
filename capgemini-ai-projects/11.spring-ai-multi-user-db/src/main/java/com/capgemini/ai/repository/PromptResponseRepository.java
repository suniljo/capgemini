package com.capgemini.ai.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.ai.entities.PromptResponse;

public interface PromptResponseRepository extends JpaRepository<PromptResponse, Long> {
    List<PromptResponse> findByUserNameOrderByCreatedAtDesc(String username);
}
