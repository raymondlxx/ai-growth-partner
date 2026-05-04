package com.aigrowth.task.entity;

import com.aigrowth.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_task")
public class Task extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;

    @Column(nullable = false)
    private Integer xp = 0;

    @Column(nullable = false)
    private String difficulty = "medium";

    @Column(nullable = false)
    private Boolean isActive = true;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Integer getXp() { return xp; }
    public void setXp(Integer xp) { this.xp = xp; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
