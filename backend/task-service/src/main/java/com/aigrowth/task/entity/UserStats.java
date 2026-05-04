package com.aigrowth.task.entity;

import com.aigrowth.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_user_stats")
public class UserStats extends BaseEntity {

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private Integer totalXp = 0;

    @Column(nullable = false)
    private Integer level = 1;

    @Column(nullable = false)
    private Integer tasksCompleted = 0;

    @Column(nullable = false)
    private Integer badgesEarned = 0;

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
}
