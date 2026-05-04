package com.aigrowth.ai.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class ChatRequest {

    @NotBlank(message = "Message is required")
    private String message;

    private String userId;

    private List<ChatMessage> history;

    public ChatRequest() {
    }

    public ChatRequest(String message, String userId, List<ChatMessage> history) {
        this.message = message;
        this.userId = userId;
        this.history = history;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ChatMessage> getHistory() {
        return history;
    }

    public void setHistory(List<ChatMessage> history) {
        this.history = history;
    }

    public static class ChatMessage {
        private String role;
        private String content;

        public ChatMessage() {
        }

        public ChatMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}