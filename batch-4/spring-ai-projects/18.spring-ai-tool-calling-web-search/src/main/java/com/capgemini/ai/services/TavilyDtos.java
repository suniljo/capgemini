package com.capgemini.ai.services;

import java.util.List;

record TavilySearchRequest(
        String api_key,
        String query,
        int max_results,
        String search_depth,
        boolean include_answer
) {
}

record TavilySearchResponse(
        String answer,
        List<TavilyResultItem> results
) {
}

record TavilyResultItem(
        String title,
        String url,
        String content
) {
}
