package com.aigrowth.path.dto;

import java.io.Serializable;

public class UserPathProgressDTO implements Serializable {
    private Long id;
    private Long userId;
    private Long pathId;
    private String status;
    private Integer progressPercent;

    public UserPathProgressDTO() {}

    public UserPathProgressDTO(Long id, Long userId, Long pathId, String status, Integer progressPercent) {
        this.id = id;
        this.userId = userId;
        this.pathId = pathId;
        this.status = status;
        this.progressPercent = progressPercent;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getPathId() { return pathId; }
    public void setPathId(Long pathId) { this.pathId = pathId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getProgressPercent() { return progressPercent; }
    public void setProgressPercent(Integer progressPercent) { this.progressPercent = progressPercent; }
}
