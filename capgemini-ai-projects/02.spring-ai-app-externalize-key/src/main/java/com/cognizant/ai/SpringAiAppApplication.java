package com.cognizant.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAiAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiAppApplication.class, args);
		
		String apiKey = System.getenv("OPENAI_API_KEY");
		System.out.println("API Key: " + apiKey);
	}

}
