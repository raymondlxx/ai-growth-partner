package com.aigrowth.content.service;

import com.aigrowth.content.dto.KnowledgeArticleRequest;
import com.aigrowth.content.dto.KnowledgeArticleResponse;
import com.aigrowth.content.entity.KnowledgeArticle;
import com.aigrowth.content.repository.KnowledgeArticleRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class KnowledgeService {

    private static final Logger log = LoggerFactory.getLogger(KnowledgeService.class);

    private final KnowledgeArticleRepository repository;

    public KnowledgeService(KnowledgeArticleRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void initSampleData() {
        if (repository.count() == 0) {
            log.info("Pre-populating knowledge base with sample articles...");
            createSampleArticles();
        }
    }

    private void createSampleArticles() {
        List<KnowledgeArticle> samples = Arrays.asList(
            createArticle(
                "Software Engineer Career Path Guide",
                "A comprehensive guide to becoming a software engineer. Start with learning programming fundamentals, data structures, and algorithms. Build projects to demonstrate skills. Contribute to open source. Prepare for interviews using leetcode and system design resources. Target FAANG companies for high growth potential.",
                "career",
                Arrays.asList("software-engineer", "programming", "career-growth", "interview-prep")
            ),
            createArticle(
                "Data Science Skill Development Roadmap",
                "Master Python and statistics first. Learn machine learning algorithms and frameworks like scikit-learn and TensorFlow. Practice with real datasets on Kaggle. Build a portfolio of projects. Understand business domain knowledge. Consider specialized roles like ML engineer or data analyst.",
                "career",
                Arrays.asList("data-science", "machine-learning", "python", "career-growth")
            ),
            createArticle(
                "Effective Communication Skills for Tech Professionals",
                "Technical skills alone are not enough. Learn to communicate complex concepts clearly. Practice writing documentation and technical blogs. Give presentations confidently. Master the art of giving and receiving feedback. Build relationships with mentors and peers. These soft skills accelerate career growth significantly.",
                "skill",
                Arrays.asList("communication", "soft-skills", "career-growth", "productivity")
            ),
            createArticle(
                "Leadership Development for Individual Contributors",
                "Leadership is not just about management roles. Lead by example, take ownership of projects, and mentor others. Develop strategic thinking by understanding business goals. Learn to influence without authority. Practice decision making with incomplete information. Build trust through consistency and integrity.",
                "skill",
                Arrays.asList("leadership", "management", "career-growth", "soft-skills")
            ),
            createArticle(
                "Product Management Fundamentals",
                "PMs bridge business, technology, and user experience. Learn to define problems clearly, prioritize features, and make data-driven decisions. Understand user research methods. Master agile methodologies and roadmapping. Build relationships with engineering and design teams. Study successful product launches to understand what works.",
                "career",
                Arrays.asList("product-management", "career-growth", "startup", "strategy")
            )
        );

        repository.saveAll(samples);
        log.info("Created {} sample knowledge articles", samples.size());
    }

    private KnowledgeArticle createArticle(String title, String content, String category, List<String> tags) {
        KnowledgeArticle article = new KnowledgeArticle(title, content, category, tags);
        article.setEmbedding(generateMockEmbedding(title + " " + content));
        return article;
    }

    private List<Double> generateMockEmbedding(String text) {
        Random random = new Random(text.hashCode());
        double[] embedding = new double[10];
        for (int i = 0; i < 10; i++) {
            embedding[i] = random.nextDouble();
        }
        normalize(embedding);
        return Arrays.stream(embedding).boxed().collect(Collectors.toList());
    }

    private void normalize(double[] vector) {
        double norm = 0;
        for (double v : vector) {
            norm += v * v;
        }
        norm = Math.sqrt(norm);
        if (norm > 0) {
            for (int i = 0; i < vector.length; i++) {
                vector[i] /= norm;
            }
        }
    }

    public KnowledgeArticleResponse createArticle(KnowledgeArticleRequest request) {
        KnowledgeArticle article = new KnowledgeArticle(
            request.getTitle(),
            request.getContent(),
            request.getCategory(),
            request.getTags()
        );
        article.setEmbedding(generateMockEmbedding(request.getTitle() + " " + request.getContent()));
        KnowledgeArticle saved = repository.save(article);
        log.info("Created knowledge article: {}", saved.getId());
        return KnowledgeArticleResponse.fromEntity(saved);
    }

    public List<KnowledgeArticleResponse> searchByQuery(String query) {
        log.info("Searching knowledge base with query: {}", query);

        List<KnowledgeArticle> allArticles = repository.findAll();

        List<Double> queryEmbedding = generateMockEmbedding(query);

        Map<KnowledgeArticle, Double> scoredArticles = new HashMap<>();
        for (KnowledgeArticle article : allArticles) {
            double score = calculateCosineSimilarity(queryEmbedding, article.getEmbedding());
            scoredArticles.put(article, score);
        }

        return scoredArticles.entrySet().stream()
            .sorted(Map.Entry.<KnowledgeArticle, Double>comparingByValue().reversed())
            .limit(10)
            .map(entry -> KnowledgeArticleResponse.fromEntity(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }

    private double calculateCosineSimilarity(List<Double> vec1, List<Double> vec2) {
        if (vec1 == null || vec2 == null || vec1.isEmpty() || vec2.isEmpty()) {
            return 0.0;
        }
        int len = Math.min(vec1.size(), vec2.size());
        double dotProduct = 0;
        for (int i = 0; i < len; i++) {
            dotProduct += vec1.get(i) * vec2.get(i);
        }

        double magnitude1 = calculateMagnitude(vec1);
        double magnitude2 = calculateMagnitude(vec2);

        if (magnitude1 == 0 || magnitude2 == 0) {
            return 0.0;
        }

        return dotProduct / (magnitude1 * magnitude2);
    }

    private double calculateMagnitude(List<Double> vector) {
        double sum = 0;
        for (Double v : vector) {
            sum += v * v;
        }
        return Math.sqrt(sum);
    }

    public List<KnowledgeArticleResponse> getAllArticles() {
        return repository.findAll().stream()
            .map(KnowledgeArticleResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public Optional<KnowledgeArticleResponse> getArticleById(String id) {
        return repository.findById(id)
            .map(KnowledgeArticleResponse::fromEntity);
    }
}