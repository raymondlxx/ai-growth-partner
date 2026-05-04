package com.aigrowth.task.controller;

import com.aigrowth.common.dto.ApiResponse;
import com.aigrowth.task.dto.*;
import com.aigrowth.task.service.TaskService;
import com.aigrowth.task.service.GamificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;
    private final GamificationService gamificationService;

    public TaskController(TaskService taskService, GamificationService gamificationService) {
        this.taskService = taskService;
        this.gamificationService = gamificationService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getAllTasks() {
        return ResponseEntity.ok(ApiResponse.success(taskService.getAllActiveTasks()));
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(taskService.getTaskById(id)));
    }

    @PostMapping("/tasks")
    public ResponseEntity<ApiResponse<TaskDTO>> createTask(@RequestBody TaskDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(taskService.createTask(dto)));
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> updateTask(@PathVariable Long id, @RequestBody TaskDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(taskService.updateTask(id, dto)));
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Task deleted"));
    }

    @PostMapping("/tasks/{id}/complete")
    public ResponseEntity<ApiResponse<TaskCompletionResultDTO>> completeTask(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") Long userId) {
        return ResponseEntity.ok(ApiResponse.success(gamificationService.completeTask(userId, id)));
    }

    @GetMapping("/user/badges")
    public ResponseEntity<ApiResponse<List<BadgeDTO>>> getUserBadges(@RequestParam Long userId) {
        return ResponseEntity.ok(ApiResponse.success(gamificationService.getUserBadges(userId)));
    }

    @GetMapping("/user/stats")
    public ResponseEntity<ApiResponse<UserStatsDTO>> getUserStats(@RequestParam Long userId) {
        return ResponseEntity.ok(ApiResponse.success(gamificationService.getUserStats(userId)));
    }
}
