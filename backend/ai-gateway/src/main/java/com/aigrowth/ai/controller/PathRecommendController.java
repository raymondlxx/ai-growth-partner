package com.aigrowth.ai.controller;

import com.aigrowth.ai.dto.PathRecommendRequest;
import com.aigrowth.ai.dto.PathRecommendResponse;
import com.aigrowth.ai.service.MockAiService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class PathRecommendController {

    private final MockAiService mockAiService;

    public PathRecommendController(MockAiService mockAiService) {
        this.mockAiService = mockAiService;
    }

    @PostMapping("/path-recommend")
    public ResponseEntity<PathRecommendResponse> recommendPath(@Valid @RequestBody PathRecommendRequest request) {
        Map<String, Object> result = mockAiService.getPathRecommendation(
            request.getUserContext(),
            request.getGoalArea(),
            request.getConstraints()
        );

        PathRecommendResponse response = new PathRecommendResponse(
            (String) result.get("recommendation"),
            (String) result.get("goalArea"),
            (String) result.get("source"),
            (Double) result.get("confidence")
        );

        return ResponseEntity.ok(response);
    }
}