package com.aigrowth.ai.dto;

public class ChatResponse {

    private String response;
    private String emotion;
    private String emotionHint;
    private String source;

    public ChatResponse() {
    }

    public ChatResponse(String response, String emotion, String emotionHint, String source) {
        this.response = response;
        this.emotion = emotion;
        this.emotionHint = emotionHint;
        this.source = source;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getEmotionHint() {
        return emotionHint;
    }

    public void setEmotionHint(String emotionHint) {
        this.emotionHint = emotionHint;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}