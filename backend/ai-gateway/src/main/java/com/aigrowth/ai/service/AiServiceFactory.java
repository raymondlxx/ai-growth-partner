package com.aigrowth.ai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Factory that routes AI chat requests to the configured provider.
 * Supported providers: mock, minimax, openai, anthropic
 */
@Service
public class AiServiceFactory {

    private static final Logger log = LoggerFactory.getLogger(AiServiceFactory.class);

    private final MockAiService mockAiService;
    private final MiniMaxAiService miniMaxAiService;

    @Value("${ai.provider:mock}")
    private String provider;

    // Track fallback state to avoid spamming logs
    private boolean miniMaxFailed = false;

    public AiServiceFactory(MockAiService mockAiService, MiniMaxAiService miniMaxAiService) {
        this.mockAiService = mockAiService;
        this.miniMaxAiService = miniMaxAiService;
    }

    public Map<String, Object> chat(String userMessage, java.util.List<Map<String, String>> history) {
        switch (provider.toLowerCase()) {
            case "minimax":
                return chatWithMiniMax(userMessage, history);
            case "openai":
            case "anthropic":
                log.warn("Provider '{}' not yet implemented, falling back to mock", provider);
                return mockAiService.chat(userMessage, history);
            default:
                return mockAiService.chat(userMessage, history);
        }
    }

    private Map<String, Object> chatWithMiniMax(String userMessage, java.util.List<Map<String, String>> history) {
        if (!miniMaxAiService.isConfigured()) {
            log.warn("MiniMax not configured (MINIMAX_API_KEY not set), using mock response");
            return mockAiService.chat(userMessage, history);
        }

        try {
            Map<String, Object> result = miniMaxAiService.chat(userMessage, history);
            Object source = result.get("source");
            if (source != null && source.toString().startsWith("minimax:")) {
                miniMaxFailed = false;
            }
            return result;
        } catch (Exception e) {
            if (!miniMaxFailed) {
                log.error("MiniMax API failed, falling back to mock: {}", e.getMessage());
                miniMaxFailed = true;
            }
            return mockAiService.chat(userMessage, history);
        }
    }

    public Map<String, Object> getPathRecommendation(String userContext, String goalArea,
                                                    java.util.List<String> constraints) {
        switch (provider.toLowerCase()) {
            case "minimax":
                if (!miniMaxAiService.isConfigured()) {
                    return mockAiService.getPathRecommendation(userContext, goalArea, constraints);
                }
                return miniMaxAiService.getPathRecommendation(userContext, goalArea, constraints);
            default:
                return mockAiService.getPathRecommendation(userContext, goalArea, constraints);
        }
    }

    public String getCurrentProvider() {
        return provider;
    }

    public boolean isMiniMaxConfigured() {
        return miniMaxAiService != null && miniMaxAiService.isConfigured();
    }
}
