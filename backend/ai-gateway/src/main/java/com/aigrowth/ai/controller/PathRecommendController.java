package com.aigrowth.ai.controller;

import com.aigrowth.ai.dto.PathRecommendRequest;
import com.aigrowth.ai.dto.PathRecommendResponse;
import com.aigrowth.ai.service.AiServiceFactory;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class PathRecommendController {

    private final AiServiceFactory aiServiceFactory;

    public PathRecommendController(AiServiceFactory aiServiceFactory) {
        this.aiServiceFactory = aiServiceFactory;
    }

    @PostMapping("/path-recommend")
    public ResponseEntity<PathRecommendResponse> recommendPath(@Valid @RequestBody PathRecommendRequest request) {
        Map<String, Object> result = aiServiceFactory.getPathRecommendation(
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
