package com.aigrowth.task.dto;

import java.io.Serializable;

public class UserStatsDTO implements Serializable {
    private Long userId;
    private Integer totalXp;
    private Integer level;
    private Integer tasksCompleted;
    private Integer badgesEarned;
    private Integer xpToNextLevel;
    private Boolean leveledUp;

    public UserStatsDTO() {}

    public UserStatsDTO(Long userId, Integer totalXp, Integer level, Integer tasksCompleted, Integer badgesEarned, Integer xpToNextLevel, Boolean leveledUp) {
        this.userId = userId;
        this.totalXp = totalXp;
        this.level = level;
        this.tasksCompleted = tasksCompleted;
        this.badgesEarned = badgesEarned;
        this.xpToNextLevel = xpToNextLevel;
        this.leveledUp = leveledUp;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Integer getTotalXp() { return totalXp; }
    public void setTotalXp(Integer totalXp) { this.totalXp = totalXp; }
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }
    public Integer getTasksCompleted() { return tasksCompleted; }
    public void setTasksCompleted(Integer tasksCompleted) { this.tasksCompleted = tasksCompleted; }
    public Integer getBadgesEarned() { return badgesEarned; }
    public void setBadgesEarned(Integer badgesEarned) { this.badgesEarned = badgesEarned; }
    public Integer getXpToNextLevel() { return xpToNextLevel; }
    public void setXpToNextLevel(Integer xpToNextLevel) { this.xpToNextLevel = xpToNextLevel; }
    public Boolean getLeveledUp() { return leveledUp; }
    public void setLeveledUp(Boolean leveledUp) { this.leveledUp = leveledUp; }
}
