package com.capgemini.ai.entities;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROMPTS_TABLE", indexes = {
        @Index(name = "idx_prompts_response_username", columnList = "userName")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromptResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String userName;

	@Lob
    @Column(nullable = false, columnDefinition = "TEXT")	
	private String prompt;
	
	@Lob
    @Column(nullable = false, columnDefinition = "TEXT")
	private String response;
	
	@Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
	
    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
