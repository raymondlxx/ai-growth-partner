package com.aigrowth.path.dto;

import java.io.Serializable;

public class SkillNodeDTO implements Serializable {
    private Long id;
    private String title;
    private String description;
    private Integer stageOrder;
    private String skillType;
    private Integer estimatedDays;
    private Boolean isRequired;
    private Boolean userCompleted;

    public SkillNodeDTO() {}

    public SkillNodeDTO(Long id, String title, String description, Integer stageOrder, String skillType, Integer estimatedDays, Boolean isRequired) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.stageOrder = stageOrder;
        this.skillType = skillType;
        this.estimatedDays = estimatedDays;
        this.isRequired = isRequired;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getStageOrder() { return stageOrder; }
    public void setStageOrder(Integer stageOrder) { this.stageOrder = stageOrder; }
    public String getSkillType() { return skillType; }
    public void setSkillType(String skillType) { this.skillType = skillType; }
    public Integer getEstimatedDays() { return estimatedDays; }
    public void setEstimatedDays(Integer estimatedDays) { this.estimatedDays = estimatedDays; }
    public Boolean getIsRequired() { return isRequired; }
    public void setIsRequired(Boolean isRequired) { this.isRequired = isRequired; }
    public Boolean getUserCompleted() { return userCompleted; }
    public void setUserCompleted(Boolean userCompleted) { this.userCompleted = userCompleted; }
}
