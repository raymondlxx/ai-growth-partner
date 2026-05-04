package com.aigrowth.task.dto;

import java.io.Serializable;

public class BadgeDTO implements Serializable {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String iconUrl;
    private String category;
    private String rarity;
    private Integer xpReward;

    public BadgeDTO() {}

    public BadgeDTO(Long id, String code, String name, String description, String iconUrl, String category, String rarity, Integer xpReward) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
        this.category = category;
        this.rarity = rarity;
        this.xpReward = xpReward;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getIconUrl() { return iconUrl; }
    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getRarity() { return rarity; }
    public void setRarity(String rarity) { this.rarity = rarity; }
    public Integer getXpReward() { return xpReward; }
    public void setXpReward(Integer xpReward) { this.xpReward = xpReward; }
}
