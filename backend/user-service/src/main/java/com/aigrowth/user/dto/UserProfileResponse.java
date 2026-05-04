package com.aigrowth.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User profile response DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    private Long id;
    private String username;
    private String email;
    private String nickname;
    private String avatar;
    private Integer status;
    private Integer level;
    private Long xp;
    private String bio;
}