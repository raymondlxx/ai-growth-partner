package com.aigrowth.task.entity;

import com.aigrowth.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_user_task_progress")
public class UserTaskProgress extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long taskId;

    @Column(nullable = false)
    private String status = "pending";

    private Integer xpEarned = 0;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getXpEarned() { return xpEarned; }
    public void setXpEarned(Integer xpEarned) { this.xpEarned = xpEarned; }
}
