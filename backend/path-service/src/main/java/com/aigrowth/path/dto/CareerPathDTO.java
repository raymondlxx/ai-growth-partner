package com.aigrowth.path.dto;

import java.io.Serializable;
import java.util.List;

public class CareerPathDTO implements Serializable {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String difficulty;
    private Boolean isActive;
    private List<SkillNodeDTO> skillNodes;

    public CareerPathDTO() {}

    public CareerPathDTO(Long id, String title, String description, String category, String difficulty, Boolean isActive) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
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
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public List<SkillNodeDTO> getSkillNodes() { return skillNodes; }
    public void setSkillNodes(List<SkillNodeDTO> skillNodes) { this.skillNodes = skillNodes; }
}
