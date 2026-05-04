package com.aigrowth.content.repository;

import com.aigrowth.content.entity.KnowledgeArticle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KnowledgeArticleRepository extends MongoRepository<KnowledgeArticle, String> {

    List<KnowledgeArticle> findByCategory(String category);

    List<KnowledgeArticle> findByTagsContaining(String tag);

    List<KnowledgeArticle> findByTitleContainingIgnoreCase(String title);
}