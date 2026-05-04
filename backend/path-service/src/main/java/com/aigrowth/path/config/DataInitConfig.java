package com.aigrowth.path.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.aigrowth.path.entity.CareerPath;
import com.aigrowth.path.entity.SkillNode;
import com.aigrowth.path.repository.CareerPathRepository;
import com.aigrowth.path.repository.SkillNodeRepository;

@Configuration
public class DataInitConfig {

    @Bean
    CommandLineRunner initCareerPaths(
            CareerPathRepository careerPathRepository,
            SkillNodeRepository skillNodeRepository) {
        return args -> {
            if (careerPathRepository.count() == 0) {
                CareerPath path1 = new CareerPath();
                path1.setTitle("前端工程师");
                path1.setDescription("从零基础到前端工程师的完整学习路径，涵盖HTML/CSS/JavaScript、框架和实战项目");
                path1.setCategory("career");
                path1.setDifficulty("medium");
                path1.setIsActive(true);
                careerPathRepository.save(path1);

                SkillNode p1s1 = new SkillNode();
                p1s1.setCareerPath(path1);
                p1s1.setTitle("HTML/CSS基础");
                p1s1.setDescription("掌握HTML标签和CSS样式");
                p1s1.setStageOrder(1);
                p1s1.setSkillType("foundation");
                p1s1.setEstimatedDays(7);
                p1s1.setIsRequired(true);
                skillNodeRepository.save(p1s1);

                SkillNode p1s2 = new SkillNode();
                p1s2.setCareerPath(path1);
                p1s2.setTitle("JavaScript核心");
                p1s2.setDescription("深入学习JavaScript语言基础和DOM操作");
                p1s2.setStageOrder(2);
                p1s2.setSkillType("core");
                p1s2.setEstimatedDays(14);
                p1s2.setIsRequired(true);
                skillNodeRepository.save(p1s2);

                SkillNode p1s3 = new SkillNode();
                p1s3.setCareerPath(path1);
                p1s3.setTitle("前端框架React");
                p1s3.setDescription("学习React组件化和Hooks");
                p1s3.setStageOrder(3);
                p1s3.setSkillType("framework");
                p1s3.setEstimatedDays(21);
                p1s3.setIsRequired(true);
                skillNodeRepository.save(p1s3);

                SkillNode p1s4 = new SkillNode();
                p1s4.setCareerPath(path1);
                p1s4.setTitle("实战项目");
                p1s4.setDescription("完成一个完整的前端项目");
                p1s4.setStageOrder(4);
                p1s4.setSkillType("project");
                p1s4.setEstimatedDays(14);
                p1s4.setIsRequired(true);
                skillNodeRepository.save(p1s4);

                CareerPath path2 = new CareerPath();
                path2.setTitle("AI产品经理");
                path2.setDescription("学习AI产品设计和管理的完整路径，从需求分析到AI项目落地");
                path2.setCategory("career");
                path2.setDifficulty("hard");
                path2.setIsActive(true);
                careerPathRepository.save(path2);

                SkillNode p2s1 = new SkillNode();
                p2s1.setCareerPath(path2);
                p2s1.setTitle("产品经理基础");
                p2s1.setDescription("产品需求分析和PRD写作");
                p2s1.setStageOrder(1);
                p2s1.setSkillType("foundation");
                p2s1.setEstimatedDays(14);
                p2s1.setIsRequired(true);
                skillNodeRepository.save(p2s1);

                SkillNode p2s2 = new SkillNode();
                p2s2.setCareerPath(path2);
                p2s2.setTitle("AI技术基础");
                p2s2.setDescription("理解机器学习、深度学习和LLM核心概念");
                p2s2.setStageOrder(2);
                p2s2.setSkillType("ai_foundation");
                p2s2.setEstimatedDays(21);
                p2s2.setIsRequired(true);
                skillNodeRepository.save(p2s2);

                SkillNode p2s3 = new SkillNode();
                p2s3.setCareerPath(path2);
                p2s3.setTitle("AI产品设计");
                p2s3.setDescription("AI产品功能设计和用户体验优化");
                p2s3.setStageOrder(3);
                p2s3.setSkillType("product_design");
                p2s3.setEstimatedDays(21);
                p2s3.setIsRequired(true);
                skillNodeRepository.save(p2s3);

                SkillNode p2s4 = new SkillNode();
                p2s4.setCareerPath(path2);
                p2s4.setTitle("AI项目实战");
                p2s4.setDescription("完成一个AI产品设计文档并落地");
                p2s4.setStageOrder(4);
                p2s4.setSkillType("project");
                p2s4.setEstimatedDays(30);
                p2s4.setIsRequired(true);
                skillNodeRepository.save(p2s4);

                CareerPath path3 = new CareerPath();
                path3.setTitle("全栈创业者");
                path3.setDescription("从技术到商业的全栈能力培养，涵盖编程、产品、运营和商业思维");
                path3.setCategory("career");
                path3.setDifficulty("hard");
                path3.setIsActive(true);
                careerPathRepository.save(path3);

                SkillNode p3s1 = new SkillNode();
                p3s1.setCareerPath(path3);
                p3s1.setTitle("全栈技术基础");
                p3s1.setDescription("前后端技术基础和数据库");
                p3s1.setStageOrder(1);
                p3s1.setSkillType("tech_foundation");
                p3s1.setEstimatedDays(30);
                p3s1.setIsRequired(true);
                skillNodeRepository.save(p3s1);

                SkillNode p3s2 = new SkillNode();
                p3s2.setCareerPath(path3);
                p3s2.setTitle("产品能力");
                p3s2.setDescription("需求分析、产品设计和项目管理");
                p3s2.setStageOrder(2);
                p3s2.setSkillType("product");
                p3s2.setEstimatedDays(21);
                p3s2.setIsRequired(true);
                skillNodeRepository.save(p3s2);

                SkillNode p3s3 = new SkillNode();
                p3s3.setCareerPath(path3);
                p3s3.setTitle("运营增长");
                p3s3.setDescription("用户增长、内容运营和数据运营");
                p3s3.setStageOrder(3);
                p3s3.setSkillType("growth");
                p3s3.setEstimatedDays(14);
                p3s3.setIsRequired(true);
                skillNodeRepository.save(p3s3);

                SkillNode p3s4 = new SkillNode();
                p3s4.setCareerPath(path3);
                p3s4.setTitle("商业思维");
                p3s4.setDescription("商业模式设计和创业融资");
                p3s4.setStageOrder(4);
                p3s4.setSkillType("business");
                p3s4.setEstimatedDays(14);
                p3s4.setIsRequired(true);
                skillNodeRepository.save(p3s4);

                SkillNode p3s5 = new SkillNode();
                p3s5.setCareerPath(path3);
                p3s5.setTitle("MVP实战");
                p3s5.setDescription("完成一个最小可行产品");
                p3s5.setStageOrder(5);
                p3s5.setSkillType("project");
                p3s5.setEstimatedDays(30);
                p3s5.setIsRequired(true);
                skillNodeRepository.save(p3s5);
            }
        };
    }
}
