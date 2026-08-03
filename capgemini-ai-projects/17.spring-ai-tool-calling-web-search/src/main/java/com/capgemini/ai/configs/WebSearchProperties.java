package com.capgemini.ai.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "web-search")
public record WebSearchProperties(
        String apiKey,
        String baseUrl,
        int defaultMaxResults
) {
    public WebSearchProperties {
        if (baseUrl == null || baseUrl.isBlank()) {
            baseUrl = "https://api.tavily.com";
        }
        if (defaultMaxResults <= 0) {
            defaultMaxResults = 5;
        }
    }
}