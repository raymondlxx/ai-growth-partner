package com.aigrowth.task.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.aigrowth.task.entity.Badge;
import com.aigrowth.task.repository.BadgeRepository;

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
}
