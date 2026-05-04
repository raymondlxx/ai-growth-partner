package com.aigrowth.ai.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class PathRecommendRequest {

    @NotBlank(message = "User context is required")
    private String userContext;

    @NotBlank(message = "Goal area is required")
    private String goalArea;

    private List<String> constraints;

    private String userId;

    public PathRecommendRequest() {
    }

    public PathRecommendRequest(String userContext, String goalArea, List<String> constraints, String userId) {
        this.userContext = userContext;
        this.goalArea = goalArea;
        this.constraints = constraints;
        this.userId = userId;
    }

    public String getUserContext() {
        return userContext;
    }

    public void setUserContext(String userContext) {
        this.userContext = userContext;
    }

    public String getGoalArea() {
        return goalArea;
    }

    public void setGoalArea(String goalArea) {
        this.goalArea = goalArea;
    }

    public List<String> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<String> constraints) {
        this.constraints = constraints;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}