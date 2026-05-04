package com.aigrowth.ai.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PromptTemplateService {

    private static final String MENTOR_SYSTEM_PROMPT = """
        You are an AI mentor companion for personal growth and career development.
        You help users navigate their career paths, develop skills, manage emotions, and achieve their goals.
        You are empathetic, supportive, and practical. You provide guidance based on the user's context and growth journey.
        Always encourage positive growth mindset and provide actionable advice.

        Guidelines:
        - Be conversational but professional
        - Ask clarifying questions when needed
        - Provide specific, actionable steps
        - Celebrate用户的进步和成就
        - Remain sensitive to用户的情绪状态
        - Suggest relevant resources or exercises when appropriate
        """;

    private static final Map<String, String> TEMPLATES = new HashMap<>();

    static {
        TEMPLATES.put("career_exploration", """
            User is exploring career options. Context: {context}
            Provide guidance on career discovery, skill assessment, and path exploration.
            """);

        TEMPLATES.put("skill_development", """
            User wants to develop skills. Context: {context}
            Provide a structured approach to skill building, including practice methods and milestones.
            """);

        TEMPLATES.put("motivation_boost", """
            User needs motivation. Context: {context}
            Provide encouraging, energetic guidance to help user regain focus and momentum.
            """);

        TEMPLATES.put("goal_planning", """
            User is setting goals. Context: {context}
            Help user define SMART goals and create actionable plans to achieve them.
            """);

        TEMPLATES.put("challenge_facing", """
            User is facing a challenge. Context: {context}
            Provide problem-solving support and practical solutions to overcome obstacles.
            """);
    }

    public String getMentorSystemPrompt() {
        return MENTOR_SYSTEM_PROMPT;
    }

    public String buildChatPrompt(String userMessage, String emotionContext, List<Map<String, String>> conversationHistory) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("System: ").append(MENTOR_SYSTEM_PROMPT).append("\n\n");

        if (emotionContext != null && !emotionContext.isEmpty()) {
            prompt.append("Emotional context: ").append(emotionContext).append("\n\n");
        }

        if (conversationHistory != null && !conversationHistory.isEmpty()) {
            prompt.append("Conversation history:\n");
            for (Map<String, String> turn : conversationHistory) {
                String role = turn.getOrDefault("role", "user");
                String content = turn.getOrDefault("content", "");
                prompt.append(role).append(": ").append(content).append("\n");
            }
        }

        prompt.append("User: ").append(userMessage);
        return prompt.toString();
    }

    public String buildPathRecommendPrompt(String userContext, String goalArea, List<String> constraints) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("System: You are an AI career path advisor helping users find optimal growth paths.\n\n");
        prompt.append("User profile context: ").append(userContext).append("\n\n");
        prompt.append("Goal area: ").append(goalArea).append("\n\n");

        if (constraints != null && !constraints.isEmpty()) {
            prompt.append("Constraints: ").append(String.join(", ", constraints)).append("\n\n");
        }

        prompt.append("Please recommend 3 potential paths with rationale, skill requirements, and milestone suggestions.");
        return prompt.toString();
    }

    public String getTemplate(String templateKey) {
        return TEMPLATES.get(templateKey);
    }

    public String fillTemplate(String templateKey, Map<String, String> variables) {
        String template = TEMPLATES.get(templateKey);
        if (template == null) {
            return null;
        }

        String result = template;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", entry.getValue() != null ? entry.getValue() : "");
        }
        return result;
    }
}