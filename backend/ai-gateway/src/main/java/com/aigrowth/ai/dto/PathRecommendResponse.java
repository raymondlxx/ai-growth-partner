package com.aigrowth.ai.dto;

public class PathRecommendResponse {

    private String recommendation;
    private String goalArea;
    private String source;
    private double confidence;

    public PathRecommendResponse() {
    }

    public PathRecommendResponse(String recommendation, String goalArea, String source, double confidence) {
        this.recommendation = recommendation;
        this.goalArea = goalArea;
        this.source = source;
        this.confidence = confidence;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getGoalArea() {
        return goalArea;
    }

    public void setGoalArea(String goalArea) {
        this.goalArea = goalArea;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}