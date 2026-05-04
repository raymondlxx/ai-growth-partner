package com.aigrowth.path.entity;

import com.aigrowth.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_skill_node")
public class SkillNode extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "path_id")
    private CareerPath careerPath;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer stageOrder;

    @Column(nullable = false)
    private String skillType;

    private Integer estimatedDays;

    @Column(nullable = false)
    private Boolean isRequired = true;

    public CareerPath getCareerPath() { return careerPath; }
    public void setCareerPath(CareerPath careerPath) { this.careerPath = careerPath; }
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
}
