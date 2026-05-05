package com.aigrowth.user.filter;

import com.aigrowth.common.response.ApiResponse;
import com.aigrowth.common.util.JwtUtil;
import com.aigrowth.user.entity.User;
import com.aigrowth.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT authentication filter
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String TOKEN_PREFIX = "user:token:";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // Skip authentication for public endpoints
        if (isPublicEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractToken(request);
        if (token == null) {
            sendUnauthorizedResponse(response, "Missing authentication token");
            return;
        }

        if (!jwtUtil.validateToken(token)) {
            log.warn("JWT validation failed for token: {}", token.substring(0, Math.min(20, token.length())));
            sendUnauthorizedResponse(response, "Invalid or expired token");
            return;
        }

        Long userId = jwtUtil.getUserIdFromToken(token);
        String storedToken = redisTemplate.opsForValue().get(TOKEN_PREFIX + userId);

        if (storedToken == null || !storedToken.equals(token)) {
            log.warn("Token mismatch or not found in Redis for userId={}", userId);
            sendUnauthorizedResponse(response, "Token has been invalidated");
            return;
        }

        request.setAttribute("userId", userId);
        request.setAttribute("token", token);

        User user = userService.findById(userId);
        if (user == null) {
            sendUnauthorizedResponse(response, "User not found");
            return;
        }

        // Set user in request attribute for controllers
        request.setAttribute("userId", userId);
        request.setAttribute("username", user.getUsername());

        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String path) {
        return path.startsWith("/api/auth/register") ||
                path.startsWith("/api/auth/login") ||
                path.startsWith("/actuator") ||
                path.equals("/health");
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        ApiResponse<Void> errorResponse = ApiResponse.error(401, message);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}