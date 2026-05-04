package com.aigrowth.ai.service;

import com.aigrowth.ai.engine.EmotionEngine;
import com.aigrowth.ai.service.PromptTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MockAiService {

    private static final Logger log = LoggerFactory.getLogger(MockAiService.class);

    private final EmotionEngine emotionEngine;
    private final PromptTemplateService promptTemplateService;

    private static final List<String> MENTOR_RESPONSES = Arrays.asList(
        "I understand you're working on your career growth. Let me help you think through this step by step. What's the biggest challenge you're facing right now?",
        "That's a great point! Building skills takes time and practice. What specific skill would you like to develop first?",
        "I can see you're motivated! Here's a suggestion: break down your big goal into smaller, manageable tasks. This helps maintain momentum.",
        "Remember, every expert was once a beginner. The key is consistent practice and learning from setbacks. What's one small step you can take today?",
        "Your growth journey is unique to you. Rather than comparing to others, focus on your own progress. What did you learn this week?",
        "It's normal to feel overwhelmed sometimes. Take a deep breath and focus on one thing at a time. What matters most right now?",
        "Great progress so far! To keep building momentum, try setting up a routine that supports your goals. What works best for you?",
        "I appreciate you sharing how you feel. Understanding your emotions is the first step to managing them effectively.",
        "Let me help you explore some options. Based on your context, here are a few approaches you might consider...",
        "Career development is a marathon, not a sprint. Celebrate the small wins along the way!"
    );

    private static final Map<String, String> PATH_RECOMMENDATIONS = new HashMap<>();

    static {
        PATH_RECOMMENDATIONS.put("software-engineer", """
            Path 1: Full Stack Developer
            - Foundation: HTML/CSS/JS, React, Node.js
            - Timeline: 6-12 months to job-ready
            - Key skills: Frontend + Backend + Database
            - Milestones: Build 3 projects, contribute to open source, prepare for interviews

            Path 2: ML/Data Engineer
            - Foundation: Python, SQL, Statistics
            - Timeline: 8-14 months to job-ready
            - Key skills: ML frameworks, data pipelines, cloud platforms
            - Milestones: Complete Kaggle projects, build portfolio, master SQL

            Path 3: DevOps/Platform Engineer
            - Foundation: Linux, Docker, Kubernetes
            - Timeline: 6-10 months to job-ready
            - Key skills: CI/CD, cloud infrastructure, monitoring
            - Milestones: Get certified, deploy production systems, contribute to infrastructure
            """);

        PATH_RECOMMENDATIONS.put("data-science", """
            Path 1: Data Analyst -> Data Scientist
            - Foundation: SQL, Python, Statistics
            - Timeline: 4-8 months to analyst, 12-18 months to scientist
            - Key skills: Excel, Tableau, pandas, visualization
            - Milestones: Build dashboard portfolio, pass SQL interviews, complete ML projects

            Path 2: ML Engineer
            - Foundation: Python, ML algorithms, software engineering
            - Timeline: 8-12 months to job-ready
            - Key skills: TensorFlow/PyTorch, model deployment, MLOps
            - Milestones: Deploy models to production, contribute to ML libraries

            Path 3: Data Engineer
            - Foundation: SQL, Python, data warehousing
            - Timeline: 6-10 months to job-ready
            - Key skills: ETL pipelines, Spark, cloud data platforms
            - Milestones: Design data architecture, optimize pipelines, get certified
            """);

        PATH_RECOMMENDATIONS.put("product-management", """
            Path 1: Technical PM
            - Foundation: Understand technical concepts, communicate with engineers
            - Timeline: 12-18 months to junior PM, 24-36 months to senior
            - Key skills: Product sense, data analysis, technical literacy
            - Milestones: Ship product features, manage small projects, develop metrics expertise

            Path 2: Growth PM
            - Foundation:数据分析, A/B testing, user psychology
            - Timeline: 10-16 months to entry-level
            - Key skills: Experimentation, conversion optimization, user research
            - Milestones: Run successful experiments, grow key metrics, build experimentation culture

            Path 3: Technical Product Manager at Startup
            - Foundation: End-to-end product ownership, fast decision making
            - Timeline: 6-12 months to entry, high velocity environment
            - Key skills: Full-stack thinking, prioritization, stakeholder management
            - Milestones: Launch MVP, achieve product-market fit, scale team
            """);

        PATH_RECOMMENDATIONS.put("default", """
            Path 1: Skill Foundation Path
            - Focus on building core competencies in your chosen domain
            - Break down learning into structured milestones
            - Practice through projects and real-world applications

            Path 2: Network & Mentorship Path
            - Find mentors and peers in your target field
            - Attend industry events and build connections
            - Learn from those already where you want to be

            Path 3: Side Project Path
            - Start building something on the side while in current role
            - Validate ideas quickly with minimal viable products
            - Learn by doing and iterate based on feedback
            """);
    }

    public MockAiService(EmotionEngine emotionEngine, PromptTemplateService promptTemplateService) {
        this.emotionEngine = emotionEngine;
        this.promptTemplateService = promptTemplateService;
    }

    public Map<String, Object> chat(String userMessage, List<Map<String, String>> history) {
        log.info("Processing mock chat for message: {}", userMessage);

        EmotionEngine.Emotion emotion = emotionEngine.detectEmotion(userMessage);
        String emotionHint = emotionEngine.getEmotionResponseHint(emotion);

        String response = selectResponseForEmotion(emotion);

        Map<String, Object> result = new HashMap<>();
        result.put("response", response);
        result.put("emotion", emotion.name());
        result.put("emotionHint", emotionHint);
        result.put("source", "mock");

        return result;
    }

    private String selectResponseForEmotion(EmotionEngine.Emotion emotion) {
        Random random = new Random();
        String baseResponse = MENTOR_RESPONSES.get(random.nextInt(MENTOR_RESPONSES.size()));

        switch (emotion) {
            case SAD:
                return "I hear you, and I want you to know that it's okay to feel this way. " + baseResponse;
            case ANXIOUS:
                return "I understand this feels overwhelming. Let's take a moment and break it down together. " + baseResponse;
            case MOTIVATED:
                return "I love your energy! You're clearly ready to make progress. " + baseResponse;
            case HAPPY:
                return "Your positive attitude is wonderful! Keep riding this wave. " + baseResponse;
            default:
                return baseResponse;
        }
    }

    public Map<String, Object> getPathRecommendation(String userContext, String goalArea, List<String> constraints) {
        log.info("Generating mock path recommendation for goal: {}", goalArea);

        String recommendation = PATH_RECOMMENDATIONS.getOrDefault(
            goalArea.toLowerCase().replace(" ", "-"),
            PATH_RECOMMENDATIONS.get("default")
        );

        Map<String, Object> result = new HashMap<>();
        result.put("recommendation", recommendation);
        result.put("goalArea", goalArea);
        result.put("source", "mock");
        result.put("confidence", 0.75);

        return result;
    }

    public Map<String, Object> getEmbeddings(String text) {
        log.debug("Generating mock embeddings for text: {}", text);

        Random random = new Random(text.hashCode());
        double[] embedding = new double[10];
        for (int i = 0; i < 10; i++) {
            embedding[i] = random.nextDouble();
        }

        double norm = 0;
        for (double v : embedding) {
            norm += v * v;
        }
        norm = Math.sqrt(norm);
        if (norm > 0) {
            for (int i = 0; i < embedding.length; i++) {
                embedding[i] /= norm;
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("embedding", embedding);
        result.put("source", "mock");

        return result;
    }
}