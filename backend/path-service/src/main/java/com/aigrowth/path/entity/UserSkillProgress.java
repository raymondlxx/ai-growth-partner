package com.aigrowth.path.entity;

import com.aigrowth.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_user_skill_progress")
public class UserSkillProgress extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long skillId;

    @Column(nullable = false)
    private Long pathId;

    @Column(nullable = false)
    private Boolean completed = false;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getSkillId() { return skillId; }
    public void setSkillId(Long skillId) { this.skillId = skillId; }
    public Long getPathId() { return pathId; }
    public void setPathId(Long pathId) { this.pathId = pathId; }
    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }
}
