package com.aigrowth.content.dto;

import com.aigrowth.content.entity.KnowledgeArticle;

import java.time.LocalDateTime;
import java.util.List;

public class KnowledgeArticleResponse {

    private String id;
    private String title;
    private String content;
    private String category;
    private List<String> tags;
    private LocalDateTime createdAt;
    private double relevanceScore;

    public KnowledgeArticleResponse() {
    }

    public static KnowledgeArticleResponse fromEntity(KnowledgeArticle article) {
        KnowledgeArticleResponse response = new KnowledgeArticleResponse();
        response.setId(article.getId());
        response.setTitle(article.getTitle());
        response.setContent(article.getContent());
        response.setCategory(article.getCategory());
        response.setTags(article.getTags());
        response.setCreatedAt(article.getCreatedAt());
        return response;
    }

    public static KnowledgeArticleResponse fromEntity(KnowledgeArticle article, double relevanceScore) {
        KnowledgeArticleResponse response = fromEntity(article);
        response.setRelevanceScore(relevanceScore);
        return response;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public double getRelevanceScore() {
        return relevanceScore;
    }

    public void setRelevanceScore(double relevanceScore) {
        this.relevanceScore = relevanceScore;
    }
}