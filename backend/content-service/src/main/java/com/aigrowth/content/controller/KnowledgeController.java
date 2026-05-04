package com.aigrowth.content.controller;

import com.aigrowth.content.dto.KnowledgeArticleRequest;
import com.aigrowth.content.dto.KnowledgeArticleResponse;
import com.aigrowth.content.service.KnowledgeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    public KnowledgeController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    @GetMapping
    public ResponseEntity<List<KnowledgeArticleResponse>> searchKnowledge(
            @RequestParam(required = false) String q) {
        if (q != null && !q.trim().isEmpty()) {
            List<KnowledgeArticleResponse> results = knowledgeService.searchByQuery(q);
            return ResponseEntity.ok(results);
        }
        List<KnowledgeArticleResponse> articles = knowledgeService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @PostMapping
    public ResponseEntity<KnowledgeArticleResponse> createArticle(
            @Valid @RequestBody KnowledgeArticleRequest request) {
        KnowledgeArticleResponse created = knowledgeService.createArticle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KnowledgeArticleResponse> getArticle(@PathVariable String id) {
        return knowledgeService.getArticleById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}