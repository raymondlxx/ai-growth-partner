package com.aigrowth.user.service;

import com.aigrowth.common.exception.BizException;
import com.aigrowth.common.util.JwtUtil;
import com.aigrowth.user.dto.*;
import com.aigrowth.user.entity.User;
import com.aigrowth.user.repository.UserRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * User service implementing register, login, profile operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final StringRedisTemplate redisTemplate;

    private static final String TOKEN_PREFIX = "***";
    private static final Duration TOKEN_TTL = Duration.ofDays(7);

    public LoginResponse register(RegisterRequest request) {
        // Auto-generate username if not provided
        String username = request.getUsername();
        if (username == null || username.isBlank()) {
            if (request.getEmail() != null && request.getEmail().contains("@")) {
                username = request.getEmail().split("@")[0];
            } else if (request.getNickname() != null && !request.getNickname().isBlank()) {
                username = request.getNickname().replaceAll("[^a-zA-Z0-9_]", "_");
            } else {
                throw BizException.badRequest("Username cannot be blank");
            }
        }
        // Ensure uniqueness by appending random suffix if needed
        String baseUsername = username;
        int suffix = 1;
        while (userRepository.selectCount(new LambdaQueryWrapper<User>().eq(User::getUsername, username)) > 0) {
            username = baseUsername + suffix++;
        }

        // Check if email already exists
        LambdaQueryWrapper<User> emailQuery = new LambdaQueryWrapper<>();
        emailQuery.eq(User::getEmail, request.getEmail());
        if (userRepository.selectCount(emailQuery) > 0) {
            throw BizException.conflict("Email already exists");
        }

        // Create user
        User user = new User();
        user.setUsername(username);
        user.setEmail(request.getEmail());
        user.setPassword(hashPassword(request.getPassword()));
        user.setNickname(request.getNickname() != null ? request.getNickname() : username);
        user.setStatus(1);
        user.setLevel(1);
        user.setXp(0L);

        userRepository.insert(user);
        log.info("User registered: id={}, username={}", user.getId(), user.getUsername());

        // Generate token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        saveToken(user.getId(), token);

        return new LoginResponse(token, user.getId(), user.getUsername(),
                user.getNickname(), user.getAvatar());
    }

    public LoginResponse login(LoginRequest request) {
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.eq(User::getUsername, request.getUsername());
        User user = userRepository.selectOne(query);

        if (user == null) {
            throw BizException.unauthorized("Invalid username or password");
        }

        if (!user.getPassword().equals(hashPassword(request.getPassword()))) {
            throw BizException.unauthorized("Invalid username or password");
        }

        if (user.getStatus() != 1) {
            throw BizException.forbidden("Account is disabled");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        saveToken(user.getId(), token);
        log.info("User logged in: id={}, username={}", user.getId(), user.getUsername());

        return new LoginResponse(token, user.getId(), user.getUsername(),
                user.getNickname(), user.getAvatar());
    }

    public UserProfileResponse getProfile(Long userId) {
        User user = userRepository.selectById(userId);
        if (user == null) {
            throw BizException.notFound("User not found");
        }

        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getNickname(),
                user.getAvatar(),
                user.getStatus(),
                user.getLevel(),
                user.getXp(),
                user.getBio()
        );
    }

    public void logout(Long userId) {
        redisTemplate.delete("user:token:" + userId);
        log.info("User logged out: id={}", userId);
    }

    public User findById(Long userId) {
        return userRepository.selectById(userId);
    }

    private void saveToken(Long userId, String token) {
        redisTemplate.opsForValue().set("user:token:" + userId, token, TOKEN_TTL);
    }

    private String hashPassword(String password) {
        return DigestUtils.md5DigestAsHex((password + "aigrowth-salt").getBytes(StandardCharsets.UTF_8));
    }
}
