package com.aigrowth.ai.engine;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class EmotionEngine {

    public enum Emotion {
        HAPPY, SAD, ANXIOUS, MOTIVATED, NEUTRAL
    }

    private static final Map<Emotion, List<String>> EMOTION_KEYWORDS = new HashMap<>();

    static {
        EMOTION_KEYWORDS.put(Emotion.HAPPY, Arrays.asList(
            "happy", "excited", "joy", "great", "wonderful", "amazing", "fantastic",
            "love", "excellent", "awesome", "brilliant", "thrilled", "delighted"
        ));
        EMOTION_KEYWORDS.put(Emotion.SAD, Arrays.asList(
            "sad", "depressed", "unhappy", "disappointed", "frustrated", "down",
            "melancholy", "gloomy", "hopeless", "lost", "broken", "hurt"
        ));
        EMOTION_KEYWORDS.put(Emotion.ANXIOUS, Arrays.asList(
            "anxious", "worried", "nervous", "stressed", "overwhelmed", "scared",
            "afraid", "panic", "tense", "uneasy", "concerned", "fear"
        ));
        EMOTION_KEYWORDS.put(Emotion.MOTIVATED, Arrays.asList(
            "motivated", "inspired", "determined", "driven", "passionate", "energetic",
            "excited", "focused", "ready", " pumped", "enthused", "goal"
        ));
    }

    public Emotion detectEmotion(String text) {
        if (text == null || text.trim().isEmpty()) {
            return Emotion.NEUTRAL;
        }

        String lowerText = text.toLowerCase();
        Map<Emotion, Integer> emotionScores = new HashMap<>();

        for (Emotion emotion : EMOTION_KEYWORDS.keySet()) {
            int score = 0;
            for (String keyword : EMOTION_KEYWORDS.get(emotion)) {
                if (lowerText.contains(keyword)) {
                    score++;
                }
            }
            emotionScores.put(emotion, score);
        }

        Emotion detectedEmotion = Emotion.NEUTRAL;
        int maxScore = 0;

        for (Map.Entry<Emotion, Integer> entry : emotionScores.entrySet()) {
            if (entry.getValue() > maxScore) {
                maxScore = entry.getValue();
                detectedEmotion = entry.getKey();
            }
        }

        return detectedEmotion;
    }

    public Map<Emotion, Double> getEmotionWeights(String text) {
        if (text == null || text.trim().isEmpty()) {
            Map<Emotion, Double> neutral = new HashMap<>();
            for (Emotion e : Emotion.values()) {
                neutral.put(e, 0.0);
            }
            neutral.put(Emotion.NEUTRAL, 1.0);
            return neutral;
        }

        String lowerText = text.toLowerCase();
        Map<Emotion, Integer> rawScores = new HashMap<>();
        int totalMatches = 0;

        for (Emotion emotion : EMOTION_KEYWORDS.keySet()) {
            int score = 0;
            for (String keyword : EMOTION_KEYWORDS.get(emotion)) {
                if (lowerText.contains(keyword)) {
                    score++;
                    totalMatches++;
                }
            }
            rawScores.put(emotion, score);
        }

        Map<Emotion, Double> weights = new HashMap<>();
        if (totalMatches == 0) {
            weights.put(Emotion.NEUTRAL, 1.0);
        } else {
            for (Emotion emotion : Emotion.values()) {
                if (emotion == Emotion.NEUTRAL) {
                    weights.put(emotion, 0.0);
                } else {
                    double weight = rawScores.getOrDefault(emotion, 0) / (double) totalMatches;
                    weights.put(emotion, Math.round(weight * 100.0) / 100.0);
                }
            }
        }

        return weights;
    }

    public String getEmotionResponseHint(Emotion emotion) {
        switch (emotion) {
            case HAPPY:
                return "User is feeling positive. Respond with enthusiasm and encouragement.";
            case SAD:
                return "User seems down. Offer empathy, support, and gentle encouragement.";
            case ANXIOUS:
                return "User is stressed or worried. Provide calm reassurance and practical suggestions.";
            case MOTIVATED:
                return "User is energized and ready. Support their momentum with actionable guidance.";
            default:
                return "User is neutral. Provide balanced, helpful guidance.";
        }
    }
}