package com.aigrowth.content.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.aigrowth.content.entity.KnowledgeArticle;
import com.aigrowth.content.repository.KnowledgeArticleRepository;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitConfig {

    @Bean
    CommandLineRunner initKnowledgeArticles(KnowledgeArticleRepository articleRepository) {
        return args -> {
            if (articleRepository.count() == 0) {
                KnowledgeArticle a1 = new KnowledgeArticle();
                a1.setTitle("前端开发完全指南");
                a1.setContent("本文档涵盖HTML、CSS、JavaScript基础，以及React、Vue等主流框架的使用方法。通过本指南的学习，你可以建立起完整的前端知识体系，并具备独立开发Web应用的能力。内容包含大量实战案例和代码示例，适合零基础学习者逐步进阶到能够独立完成项目的前端工程师。");
                a1.setCategory("技术");
                a1.setTags(Arrays.asList("前端", "HTML", "CSS", "JavaScript", "React"));
                a1.setCreatedAt(java.time.LocalDateTime.now());
                a1.setUpdatedAt(java.time.LocalDateTime.now());
                articleRepository.save(a1);

                KnowledgeArticle a2 = new KnowledgeArticle();
                a2.setTitle("AI产品经理成长手册");
                a2.setContent("本手册面向希望转型为AI产品经理的技术人员或产品经理。内容涵盖AI基础概念、机器学习原理、大语言模型应用、产品需求分析、AI项目管理等核心知识。通过系统的学习和实践，你将能够更好地理解AI技术边界，设计出可行的AI产品方案，并有效管理AI项目的全流程。");
                a2.setCategory("产品");
                a2.setTags(Arrays.asList("AI产品", "产品经理", "机器学习", "项目管理"));
                a2.setCreatedAt(java.time.LocalDateTime.now());
                a2.setUpdatedAt(java.time.LocalDateTime.now());
                articleRepository.save(a2);

                KnowledgeArticle a3 = new KnowledgeArticle();
                a3.setTitle("创业者技术必修课");
                a3.setContent("作为创业者，你需要了解技术以便更好地与工程师沟通、评估项目可行性和做出正确的技术决策。本课程涵盖全栈技术基础（前后端、数据库、服务器）、常见技术栈对比、项目技术选型策略等内容。帮助你建立技术世界观，理解技术团队的工作方式，提升创业成功率。");
                a3.setCategory("创业");
                a3.setTags(Arrays.asList("创业", "全栈", "技术选型", "商业思维"));
                a3.setCreatedAt(java.time.LocalDateTime.now());
                a3.setUpdatedAt(java.time.LocalDateTime.now());
                articleRepository.save(a3);

                KnowledgeArticle a4 = new KnowledgeArticle();
                a4.setTitle("职场软技能提升指南");
                a4.setContent("技术能力决定你的下限，软技能决定你的上限。本指南涵盖沟通技巧、团队协作、时间管理、情绪控制等职场必备软技能。通过理论学习和实践练习，帮助你成为不仅技术过硬，而且职场人际关系良好的全面型人才。适合所有希望提升职业竞争力的工作者。");
                a4.setCategory("职场");
                a4.setTags(Arrays.asList("软技能", "沟通", "协作", "时间管理"));
                a4.setCreatedAt(java.time.LocalDateTime.now());
                a4.setUpdatedAt(java.time.LocalDateTime.now());
                articleRepository.save(a4);

                KnowledgeArticle a5 = new KnowledgeArticle();
                a5.setTitle("学习能力提升方法论");
                a5.setContent("在这个快速变化的时代，学会学习比学习本身更重要。本文章介绍费曼学习法、刻意练习、间隔重复等科学学习方法，以及如何构建个人知识管理系统。通过掌握这些方法，你将大幅提升学习效率，更好地吸收新知识，形成自己的知识体系。");
                a5.setCategory("学习方法");
                a5.setTags(Arrays.asList("学习方法", "费曼技巧", "知识管理", "自我提升"));
                a5.setCreatedAt(java.time.LocalDateTime.now());
                a5.setUpdatedAt(java.time.LocalDateTime.now());
                articleRepository.save(a5);

                KnowledgeArticle a6 = new KnowledgeArticle();
                a6.setTitle("个人成长OKR制定指南");
                a6.setContent("OKR（目标与关键结果）不仅是企业管理的工具，也可以用于个人成长规划。本指南介绍如何运用OKR进行个人目标设定、分解和跟踪。通过明确的目​​标和可衡量的关键结果，帮助你保持成长方向感，定期回顾和调整计划，最终实现个人发展的突破。");
                a6.setCategory("方法论");
                a6.setTags(Arrays.asList("OKR", "目标管理", "个人成长", "规划"));
                a6.setCreatedAt(java.time.LocalDateTime.now());
                a6.setUpdatedAt(java.time.LocalDateTime.now());
                articleRepository.save(a6);
            }
        };
    }
}