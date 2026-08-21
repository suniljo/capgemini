package com.capgemini.ai.tools;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.capgemini.ai.dto.WebSearchResult;
import com.capgemini.ai.services.WebSearchProvider;

/**
 * Exposes web search to the model as a callable tool. Spring AI inspects the
 * @Tool / @ToolParam annotations to build the JSON schema sent to OpenAI, and
 * invokes this method automatically whenever the model decides it needs
 * current information it doesn't already have.
 */
@Component
public class WebSearchTool {

    private static final Logger log = LoggerFactory.getLogger(WebSearchTool.class);

    private final WebSearchProvider webSearchProvider;

    public WebSearchTool(WebSearchProvider webSearchProvider) {
        this.webSearchProvider = webSearchProvider;
    }

    @Tool(description = """
            Search the public web for current or factual information the model may not already know \
            (recent events, prices, releases, people, current status of something, etc). \
            Returns a short list of results with title, url, and a text snippet. \
            Use this whenever the answer could be time-sensitive or outside your training data.""")
    public String searchWeb(
            @ToolParam(description = "The search query, phrased like a search engine query, not a full sentence")
            String query,
            @ToolParam(description = "How many results to return, between 1 and 10", required = false)
            Integer maxResults
    ) {
        log.info("Tool call: searchWeb(query='{}', maxResults={})", query, maxResults);

        int resolvedMax = (maxResults == null) ? 5 : Math.min(10, Math.max(1, maxResults));
        List<WebSearchResult> results = webSearchProvider.search(query, resolvedMax);

        if (results.isEmpty()) {
            return "No web search results were found for that query.";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < results.size(); i++) {
            WebSearchResult r = results.get(i);
            sb.append(i + 1).append(". ").append(r.title()).append('\n')
              .append("   URL: ").append(r.url()).append('\n')
              .append("   Snippet: ").append(r.snippet()).append('\n');
        }
        return sb.toString();
    }
}
