package com.aigrowth.task.dto;

import java.io.Serializable;

public class TaskDTO implements Serializable {
    private Long id;
    private String title;
    private String description;
    private String category;
    private Integer xp;
    private String difficulty;
    private Boolean isActive;

    public TaskDTO() {}

    public TaskDTO(Long id, String title, String description, String category, Integer xp, String difficulty, Boolean isActive) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.xp = xp;
        this.difficulty = difficulty;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
