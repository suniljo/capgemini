package com.capgemini.ai.services;

import java.util.List;

import com.capgemini.ai.dto.WebSearchResult;

/**
 * Abstraction over whichever search API backs the tool, so swapping providers
 * (Tavily, Bing, Serper, Google CSE, ...) doesn't touch the tool-calling code.
 */
public interface WebSearchProvider {

    List<WebSearchResult> search(String query, int maxResults);
}
