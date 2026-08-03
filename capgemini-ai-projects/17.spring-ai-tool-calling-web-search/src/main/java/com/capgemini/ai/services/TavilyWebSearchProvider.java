package com.capgemini.ai.services;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.capgemini.ai.configs.WebSearchProperties;
import com.capgemini.ai.dto.WebSearchResult;

/**
 * Calls the Tavily Search API (https://tavily.com), which is purpose-built for
 * feeding results to LLMs. Swap this out for another {@link WebSearchProvider}
 * implementation (Bing, Serper, Google CSE, ...) without touching the tool or
 * the ChatClient wiring.
 */
@Service
public class TavilyWebSearchProvider implements WebSearchProvider {

    private static final Logger log = LoggerFactory.getLogger(TavilyWebSearchProvider.class);

    private final WebClient webClient;
    private final WebSearchProperties properties;

    public TavilyWebSearchProvider(WebClient webSearchWebClient, WebSearchProperties properties) {
        this.webClient = webSearchWebClient;
        this.properties = properties;
    }

    @Override
    public List<WebSearchResult> search(String query, int maxResults) {
    	System.out.println(query+"\n"+properties.apiKey());
        if (properties.apiKey() == null || properties.apiKey().isBlank()) {
            log.warn("web-search.api-key is not configured; returning no results for query '{}'", query);
            return Collections.emptyList();
        }

        int resolvedMaxResults = maxResults > 0 ? maxResults : properties.defaultMaxResults();

        TavilySearchRequest request = new TavilySearchRequest(
                properties.apiKey(),
                query,
                resolvedMaxResults,
                "basic",
                false
        );

        try {
            TavilySearchResponse response = webClient.post()
                    .uri("/search")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(TavilySearchResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();

            if (response == null || response.results() == null) {
                return Collections.emptyList();
            }

            return response.results().stream()
                    .map(item -> new WebSearchResult(item.title(), item.url(), item.content()))
                    .toList();

        } catch (Exception ex) {
            log.warn("Web search failed for query '{}': {}", query, ex.getMessage());
            return Collections.emptyList();
        }
    }
}
