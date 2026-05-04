package com.aigrowth.task.repository;

import com.aigrowth.task.entity.BadgeAward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BadgeAwardRepository extends JpaRepository<BadgeAward, Long> {
    List<BadgeAward> findByUserId(Long userId);
    boolean existsByUserIdAndBadgeId(Long userId, Long badgeId);
}
