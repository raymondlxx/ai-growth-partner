package com.aigrowth.path.controller;

import com.aigrowth.common.dto.ApiResponse;
import com.aigrowth.path.dto.CareerPathDTO;
import com.aigrowth.path.dto.UserPathProgressDTO;
import com.aigrowth.path.service.PathService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/paths")
public class PathController {

    private final PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CareerPathDTO>>> getAllPaths() {
        return ResponseEntity.ok(ApiResponse.success(pathService.getAllActivePaths()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CareerPathDTO>> getPathById(
            @PathVariable Long id,
            @RequestParam(required = false) Long userId) {
        if (userId != null) {
            return ResponseEntity.ok(ApiResponse.success(pathService.getPathByIdWithUserProgress(id, userId)));
        }
        return ResponseEntity.ok(ApiResponse.success(pathService.getPathById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CareerPathDTO>> createPath(@RequestBody CareerPathDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(pathService.createPath(dto)));
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<ApiResponse<UserPathProgressDTO>> startPath(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") Long userId) {
        return ResponseEntity.ok(ApiResponse.success(pathService.startPath(userId, id)));
    }

    @PutMapping("/{id}/skill/{skillId}/complete")
    public ResponseEntity<ApiResponse<UserPathProgressDTO>> completeSkill(
            @PathVariable Long id,
            @PathVariable Long skillId,
            @RequestParam(defaultValue = "1") Long userId) {
        return ResponseEntity.ok(ApiResponse.success(pathService.completeSkill(userId, id, skillId)));
    }
}
