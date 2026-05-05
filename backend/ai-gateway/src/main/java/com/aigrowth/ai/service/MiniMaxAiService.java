package com.aigrowth.ai.service;

import com.aigrowth.ai.engine.EmotionEngine;
import com.aigrowth.ai.service.PromptTemplateService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;

/**
 * MiniMax AI Service - Direct HTTP calls to MiniMax OpenAI-compatible API.
 *
 * MiniMax API docs: https://www.minimaxi.com/document/Guides/Getting%20Started
 * API Base: https://api.minimax.chat/v1
 * Model: MiniMax-M2.7 (supports 128K context)
 */
@Service
public class MiniMaxAiService {

    private static final Logger log = LoggerFactory.getLogger(MiniMaxAiService.class);

    private static final String CHAT_COMPLETIONS_URL =
        "https://api.minimax.chat/v1/text/chatcompletion_v2";

    private final EmotionEngine emotionEngine;
    private final PromptTemplateService promptTemplateService;
    private final ObjectMapper objectMapper;

    @Value("${ai.minimax.api-key:}")
    private String apiKey;

    @Value("${ai.minimax.group-id:}")
    private String groupId;

    @Value("${ai.minimax.model:minimax-01-16k}")
    private String model;

    @Value("${ai.mentor.system-prompt:}")
    private String systemPrompt;

    @Value("${ai.chat.temperature:0.7}")
    private double temperature;

    private final HttpClient httpClient;

    public MiniMaxAiService(EmotionEngine emotionEngine, PromptTemplateService promptTemplateService) {
        this.emotionEngine = emotionEngine;
        this.promptTemplateService = promptTemplateService;
        this.objectMapper = new ObjectMapper();
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .build();
    }

    public Map<String, Object> chat(String userMessage, List<Map<String, String>> history) {
        log.info("MiniMax chat request: msgLen={}, historyLen={}", userMessage.length(),
            history != null ? history.size() : 0);

        EmotionEngine.Emotion emotion = emotionEngine.detectEmotion(userMessage);
        String emotionHint = emotionEngine.getEmotionResponseHint(emotion);

        try {
            String responseText = callMiniMaxApi(userMessage, history, emotion, emotionHint);

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("response", responseText);
            result.put("emotion", emotion.name());
            result.put("emotionHint", emotionHint);
            result.put("source", "minimax:" + model);
            return result;

        } catch (Exception e) {
            log.error("MiniMax API call failed: {}", e.getMessage());
            return buildFallbackResult(emotion, emotionHint, e.getMessage());
        }
    }

    private String callMiniMaxApi(String userMessage, List<Map<String, String>> history,
                                  EmotionEngine.Emotion emotion, String emotionHint)
        throws IOException, InterruptedException {

        // Build messages array
        List<Map<String, Object>> messages = new ArrayList<>();

        // System prompt with emotion context
        String fullSystemPrompt = systemPrompt.trim() + "\n\n" +
            "Current user emotional state: " + emotion.name() + "\n" +
            "Emotional guidance: " + emotionHint;
        messages.add(Map.of("role", "system", "content", fullSystemPrompt));

        // History
        if (history != null) {
            for (Map<String, String> turn : history) {
                messages.add(Map.of(
                    "role", turn.getOrDefault("role", "user"),
                    "content", turn.getOrDefault("content", "")
                ));
            }
        }

        // Current user message
        messages.add(Map.of("role", "user", "content", userMessage));

        // Build request body
        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", messages);
        requestBody.put("temperature", temperature);
        requestBody.put("max_tokens", 1024);

        String jsonBody = objectMapper.writeValueAsString(requestBody);
        log.debug("MiniMax request body: {}", jsonBody);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(CHAT_COMPLETIONS_URL))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + apiKey)
            .header("minimax-group-id", groupId)
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .timeout(Duration.ofSeconds(60))
            .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        log.debug("MiniMax response status: {}", response.statusCode());

        if (response.statusCode() != 200) {
            String body = response.body();
            // Try to extract MiniMax error code and message
            try {
                JsonNode respNode = objectMapper.readTree(body);
                JsonNode baseResp = respNode.path("base_resp");
                if (!baseResp.isMissingNode()) {
                    int statusCode = baseResp.path("status_code").asInt(-1);
                    String statusMsg = baseResp.path("status_msg").asText("");
                    if (statusCode != 0) {
                        throw new RuntimeException("MiniMax API error " + statusCode +
                            " (1004=invalid key, 1007=rate limit): " + statusMsg);
                    }
                }
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception ignored) {}
            throw new RuntimeException("MiniMax API error: HTTP " + response.statusCode() +
                ", body: " + body);
        }

        return parseMiniMaxResponse(response.body());
    }

    private String parseMiniMaxResponse(String json) throws IOException {
        // MiniMax response: {"choices":[{"finish_reason":"stop","index":0,
        //   "message":{"content":"...","role":"assistant","name":"MiniMax AI",
        //              "reasoning_content":"..."}}]}
        // We want message.content (NOT reasoning_content)
        JsonNode root = objectMapper.readTree(json);

        JsonNode choices = root.path("choices");
        if (choices.isMissingNode() || !choices.isArray() || choices.isEmpty()) {
            throw new RuntimeException("No choices in MiniMax response");
        }

        JsonNode firstChoice = choices.get(0);
        JsonNode message = firstChoice.path("message");
        if (message.isMissingNode()) {
            throw new RuntimeException("No 'message' in choices[0]");
        }

        String content = message.path("content").asText(null);
        if (content == null || content.isBlank()) {
            throw new RuntimeException("No 'content' in message object");
        }

        return content;
    }

    private Map<String, Object> buildFallbackResult(EmotionEngine.Emotion emotion,
                                                     String emotionHint, String error) {
        String response;
        switch (emotion) {
            case SAD:      response = "抱歉，AI服务暂时不可用（"+error+"）。但请记住，遇到困难是成长的一部分，不要因此气馁。"; break;
            case ANXIOUS:  response = "我理解你可能感到焦虑。请深呼吸，专注于眼前可以控制的一小步。AI服务恢复后我们再继续。"; break;
            case MOTIVATED:response = "你的动力和热情让我印象深刻！虽然AI服务暂时中断，但你的进取心是最重要的。"; break;
            case HAPPY:    response = "很高兴看到你这么积极！即使AI服务遇到了一点问题，也不影响你的好心情。"; break;
            default:       response = "AI导师暂时不可用（"+error+"），请稍后再试。感谢你的耐心。";
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("response", response);
        result.put("emotion", emotion.name());
        result.put("emotionHint", emotionHint);
        result.put("source", "fallback");
        return result;
    }

    public Map<String, Object> getPathRecommendation(String userContext, String goalArea,
                                                     List<String> constraints) {
        String prompt = promptTemplateService.buildPathRecommendPrompt(userContext, goalArea, constraints);

        try {
            List<Map<String, String>> history = List.of(
                Map.of("role", "system", "content",
                    "You are a career path advisor. Provide structured, actionable path recommendations."),
                Map.of("role", "user", "content", prompt)
            );

            String responseText = callMiniMaxApi(prompt, history,
                EmotionEngine.Emotion.NEUTRAL, "");

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("recommendation", responseText);
            result.put("goalArea", goalArea);
            result.put("source", "minimax:" + model);
            result.put("confidence", 0.85);
            return result;

        } catch (Exception e) {
            log.error("MiniMax path recommendation failed: {}", e.getMessage());
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("recommendation", "Path recommendation service temporarily unavailable.");
            result.put("goalArea", goalArea);
            result.put("source", "fallback");
            result.put("confidence", 0.0);
            return result;
        }
    }

    public boolean isConfigured() {
        return apiKey != null && !apiKey.isBlank() && !apiKey.equals("your-minimax-key");
    }
}
