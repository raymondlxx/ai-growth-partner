package com.aigrowth.task.entity;

import com.aigrowth.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_badge_award")
public class BadgeAward extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long badgeId;

    private String source;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getBadgeId() { return badgeId; }
    public void setBadgeId(Long badgeId) { this.badgeId = badgeId; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}
