package com.aigrowth.task.service;

import com.aigrowth.task.dto.*;
import com.aigrowth.task.entity.*;
import com.aigrowth.task.repository.*;
import com.aigrowth.common.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GamificationService {

    private final UserStatsRepository userStatsRepository;
    private final UserTaskProgressRepository userTaskProgressRepository;
    private final BadgeRepository badgeRepository;
    private final BadgeAwardRepository badgeAwardRepository;
    private final TaskRepository taskRepository;

    public GamificationService(
            UserStatsRepository userStatsRepository,
            UserTaskProgressRepository userTaskProgressRepository,
            BadgeRepository badgeRepository,
            BadgeAwardRepository badgeAwardRepository,
            TaskRepository taskRepository) {
        this.userStatsRepository = userStatsRepository;
        this.userTaskProgressRepository = userTaskProgressRepository;
        this.badgeRepository = badgeRepository;
        this.badgeAwardRepository = badgeAwardRepository;
        this.taskRepository = taskRepository;
    }

    public TaskCompletionResultDTO completeTask(Long userId, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(404, "Task not found"));

        UserTaskProgress progress = userTaskProgressRepository.findByUserIdAndTaskId(userId, taskId)
                .orElseGet(() -> {
                    UserTaskProgress p = new UserTaskProgress();
                    p.setUserId(userId);
                    p.setTaskId(taskId);
                    p.setStatus("pending");
                    return p;
                });

        if ("completed".equals(progress.getStatus())) {
            throw new BusinessException(400, "Task already completed");
        }

        int xpEarned = task.getXp();
        progress.setStatus("completed");
        progress.setXpEarned(xpEarned);
        userTaskProgressRepository.save(progress);

        UserStats stats = getOrCreateUserStats(userId);
        int oldLevel = stats.getLevel();
        stats.setTotalXp(stats.getTotalXp() + xpEarned);
        stats.setTasksCompleted(stats.getTasksCompleted() + 1);
        int newLevel = calculateLevel(stats.getTotalXp());
        boolean leveledUp = newLevel > oldLevel;
        stats.setLevel(newLevel);
        userStatsRepository.save(stats);

        BadgeDTO newBadge = checkAndAwardBadges(userId, stats);

        int xpForNext = xpForLevel(newLevel + 1);
        int currentXpInLevel = stats.getTotalXp() - xpForLevel(oldLevel);
        int xpToNext = xpForNext - stats.getTotalXp();

        UserStatsDTO statsDTO = new UserStatsDTO(
                stats.getUserId(),
                stats.getTotalXp(),
                stats.getLevel(),
                stats.getTasksCompleted(),
                stats.getBadgesEarned(),
                xpToNext,
                leveledUp
        );

        TaskCompletionResultDTO result = new TaskCompletionResultDTO();
        result.setTaskId(taskId);
        result.setXpAwarded(xpEarned);
        result.setLeveledUp(leveledUp);
        result.setNewLevel(newLevel);
        result.setNewBadge(newBadge);
        result.setUpdatedStats(statsDTO);
        return result;
    }

    public int calculateLevel(int totalXp) {
        if (totalXp < 100) return 1;
        int level = 1;
        int xpSum = 0;
        int n = 1;
        while (xpSum + (level * n * 100) <= totalXp) {
            xpSum += level * n * 100;
            level++;
            n++;
        }
        return level;
    }

    public int xpForLevel(int level) {
        if (level <= 1) return 0;
        int total = 0;
        for (int i = 1; i < level; i++) {
            total += i * 100;
        }
        return total;
    }

    private UserStats getOrCreateUserStats(Long userId) {
        return userStatsRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserStats stats = new UserStats();
                    stats.setUserId(userId);
                    stats.setTotalXp(0);
                    stats.setLevel(1);
                    stats.setTasksCompleted(0);
                    stats.setBadgesEarned(0);
                    return userStatsRepository.save(stats);
                });
    }

    private BadgeDTO checkAndAwardBadges(Long userId, UserStats stats) {
        List<Badge> allBadges = badgeRepository.findAll();
        for (Badge badge : allBadges) {
            if (badgeAwardRepository.existsByUserIdAndBadgeId(userId, badge.getId())) {
                continue;
            }
            boolean earned = false;
            String criteria = badge.getCriteria();
            if (criteria != null) {
                if (criteria.contains("\"tasks\":10") && stats.getTasksCompleted() >= 10) {
                    earned = true;
                } else if (criteria.contains("\"level\":5") && stats.getLevel() >= 5) {
                    earned = true;
                } else if (criteria.contains("\"xp\":500") && stats.getTotalXp() >= 500) {
                    earned = true;
                }
            }
            if (earned) {
                awardBadge(userId, badge.getId());
                return toBadgeDTO(badge);
            }
        }
        return null;
    }

    public BadgeDTO awardBadge(Long userId, Long badgeId) {
        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(() -> new BusinessException(404, "Badge not found"));

        if (badgeAwardRepository.existsByUserIdAndBadgeId(userId, badgeId)) {
            throw new BusinessException(400, "Badge already earned");
        }

        BadgeAward award = new BadgeAward();
        award.setUserId(userId);
        award.setBadgeId(badgeId);
        award.setSource("task_completion");
        badgeAwardRepository.save(award);

        UserStats stats = getOrCreateUserStats(userId);
        stats.setTotalXp(stats.getTotalXp() + badge.getXpReward());
        stats.setBadgesEarned(stats.getBadgesEarned() + 1);
        userStatsRepository.save(stats);

        return toBadgeDTO(badge);
    }

    public List<BadgeDTO> getUserBadges(Long userId) {
        List<BadgeAward> awards = badgeAwardRepository.findByUserId(userId);
        return awards.stream()
                .map(award -> badgeRepository.findById(award.getBadgeId()).orElse(null))
                .filter(b -> b != null)
                .map(this::toBadgeDTO)
                .collect(Collectors.toList());
    }

    public UserStatsDTO getUserStats(Long userId) {
        UserStats stats = getOrCreateUserStats(userId);
        int xpToNext = xpForLevel(stats.getLevel() + 1) - stats.getTotalXp();
        return new UserStatsDTO(
                stats.getUserId(),
                stats.getTotalXp(),
                stats.getLevel(),
                stats.getTasksCompleted(),
                stats.getBadgesEarned(),
                xpToNext,
                false
        );
    }

    private BadgeDTO toBadgeDTO(Badge badge) {
        return new BadgeDTO(
                badge.getId(),
                badge.getCode(),
                badge.getName(),
                badge.getDescription(),
                badge.getIconUrl(),
                badge.getCategory(),
                badge.getRarity(),
                badge.getXpReward()
        );
    }
}
