package com.aigrowth.user.controller;

import com.aigrowth.common.response.ApiResponse;
import com.aigrowth.user.dto.*;
import com.aigrowth.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * User REST controller for authentication and profile management
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Register a new user
     */
    @PostMapping("/auth/register")
    public ApiResponse<LoginResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Register request: username={}", request.getUsername());
        LoginResponse response = userService.register(request);
        return ApiResponse.success(response, "Registration successful");
    }

    /**
     * Login with username and password
     */
    @PostMapping("/auth/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login request: username={}", request.getUsername());
        LoginResponse response = userService.login(request);
        return ApiResponse.success(response, "Login successful");
    }

    /**
     * Get current user profile
     */
    @GetMapping("/user/profile")
    public ApiResponse<UserProfileResponse> getProfile(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        UserProfileResponse profile = userService.getProfile(userId);
        return ApiResponse.success(profile);
    }

    /**
     * Logout current user
     */
    @PostMapping("/auth/logout")
    public ApiResponse<Void> logout(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        userService.logout(userId);
        return ApiResponse.success(null, "Logout successful");
    }
}