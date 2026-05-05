package com.aigrowth.task.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.aigrowth.task.entity.Badge;
import com.aigrowth.task.entity.Task;
import com.aigrowth.task.repository.BadgeRepository;
import com.aigrowth.task.repository.TaskRepository;

@Configuration
public class DataInitConfig {

    @Bean
    CommandLineRunner initBadges(BadgeRepository badgeRepository) {
        return args -> {
            if (badgeRepository.count() == 0) {
                Badge b1 = new Badge();
                b1.setCode("FIRST_STEP");
                b1.setName("First Step");
                b1.setDescription("Complete your first task");
                b1.setCategory("milestone");
                b1.setRarity("common");
                b1.setXpReward(10);
                b1.setCriteria("{\"type\":\"task_count\",\"tasks\":1}");
                badgeRepository.save(b1);

                Badge b2 = new Badge();
                b2.setCode("TASK_MASTER");
                b2.setName("Task Master");
                b2.setDescription("Complete 10 tasks");
                b2.setCategory("achievement");
                b2.setRarity("rare");
                b2.setXpReward(50);
                b2.setCriteria("{\"type\":\"task_count\",\"tasks\":10}");
                badgeRepository.save(b2);

                Badge b3 = new Badge();
                b3.setCode("LEVEL_5");
                b3.setName("Rising Star");
                b3.setDescription("Reach level 5");
                b3.setCategory("milestone");
                b3.setRarity("epic");
                b3.setXpReward(100);
                b3.setCriteria("{\"type\":\"level\",\"level\":5}");
                badgeRepository.save(b3);

                Badge b4 = new Badge();
                b4.setCode("XP_HUNTER");
                b4.setName("XP Hunter");
                b4.setDescription("Earn 500 XP total");
                b4.setCategory("achievement");
                b4.setRarity("rare");
                b4.setXpReward(50);
                b4.setCriteria("{\"type\":\"xp\",\"xp\":500}");
                badgeRepository.save(b4);
            }
        };
    }

    @Bean
    CommandLineRunner initTasks(TaskRepository taskRepository) {
        return args -> {
            if (taskRepository.count() == 0) {
                Task t1 = new Task();
                t1.setTitle("完成每日阅读计划");
                t1.setDescription("每天阅读技术文章至少30分钟，并做好笔记整理");
                t1.setCategory("daily");
                t1.setXp(20);
                t1.setDifficulty("easy");
                t1.setIsActive(true);
                taskRepository.save(t1);

                Task t2 = new Task();
                t2.setTitle("完成一个代码挑战");
                t2.setDescription("在LeetCode或类似平台完成一道算法题");
                t2.setCategory("challenge");
                t2.setXp(30);
                t2.setDifficulty("medium");
                t2.setIsActive(true);
                taskRepository.save(t2);

                Task t3 = new Task();
                t3.setTitle("写一篇学习总结");
                t3.setDescription("整理本周学习内容，撰写一篇不少于500字的技术总结");
                t3.setCategory("writing");
                t3.setXp(40);
                t3.setDifficulty("medium");
                t3.setIsActive(true);
                taskRepository.save(t3);

                Task t4 = new Task();
                t4.setTitle("完成一个项目模块");
                t4.setDescription("完成项目中一个独立功能模块的开发");
                t4.setCategory("project");
                t4.setXp(100);
                t4.setDifficulty("hard");
                t4.setIsActive(true);
                taskRepository.save(t4);

                Task t5 = new Task();
                t5.setTitle("参与技术讨论");
                t5.setDescription("在团队讨论中发表至少3条有价值的技術观点");
                t5.setCategory("social");
                t5.setXp(15);
                t5.setDifficulty("easy");
                t5.setIsActive(true);
                taskRepository.save(t5);

                Task t6 = new Task();
                t6.setTitle("学习新技能点");
                t6.setDescription("学习一个技术栈中的新知识点并完成练习");
                t6.setCategory("learning");
                t6.setXp(50);
                t6.setDifficulty("medium");
                t6.setIsActive(true);
                taskRepository.save(t6);

                Task t7 = new Task();
                t7.setTitle("代码审查");
                t7.setDescription("为团队其他成员的代码提供审查意见");
                t7.setCategory("review");
                t7.setXp(25);
                t7.setDifficulty("easy");
                t7.setIsActive(true);
                taskRepository.save(t7);

                Task t8 = new Task();
                t8.setTitle("完成高级项目挑战");
                t8.setDescription("独立完成一个完整的高级项目，包括设计、开发和部署");
                t8.setCategory("project");
                t8.setXp(200);
                t8.setDifficulty("hard");
                t8.setIsActive(true);
                taskRepository.save(t8);
            }
        };
    }
}
