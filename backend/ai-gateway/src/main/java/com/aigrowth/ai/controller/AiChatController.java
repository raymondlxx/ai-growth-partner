package com.aigrowth.ai.controller;

import com.aigrowth.ai.dto.ChatRequest;
import com.aigrowth.ai.dto.ChatResponse;
import com.aigrowth.ai.service.MockAiService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/ai")
public class AiChatController {

    private final MockAiService mockAiService;

    public AiChatController(MockAiService mockAiService) {
        this.mockAiService = mockAiService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        List<Map<String, String>> history = new ArrayList<>();
        if (request.getHistory() != null) {
            for (ChatRequest.ChatMessage msg : request.getHistory()) {
                Map<String, String> turn = new HashMap<>();
                turn.put("role", msg.getRole());
                turn.put("content", msg.getContent());
                history.add(turn);
            }
        }

        Map<String, Object> result = mockAiService.chat(request.getMessage(), history);

        ChatResponse response = new ChatResponse(
            (String) result.get("response"),
            (String) result.get("emotion"),
            (String) result.get("emotionHint"),
            (String) result.get("source")
        );

        return ResponseEntity.ok(response);
    }
}