package com.aigrowth.task.dto;

import java.io.Serializable;
import java.util.List;

public class TaskCompletionResultDTO implements Serializable {
    private Long taskId;
    private Integer xpAwarded;
    private Boolean leveledUp;
    private Integer newLevel;
    private BadgeDTO newBadge;
    private UserStatsDTO updatedStats;

    public TaskCompletionResultDTO() {}

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }
    public Integer getXpAwarded() { return xpAwarded; }
    public void setXpAwarded(Integer xpAwarded) { this.xpAwarded = xpAwarded; }
    public Boolean getLeveledUp() { return leveledUp; }
    public void setLeveledUp(Boolean leveledUp) { this.leveledUp = leveledUp; }
    public Integer getNewLevel() { return newLevel; }
    public void setNewLevel(Integer newLevel) { this.newLevel = newLevel; }
    public BadgeDTO getNewBadge() { return newBadge; }
    public void setNewBadge(BadgeDTO newBadge) { this.newBadge = newBadge; }
    public UserStatsDTO getUpdatedStats() { return updatedStats; }
    public void setUpdatedStats(UserStatsDTO updatedStats) { this.updatedStats = updatedStats; }
}
