package com.aigrowth.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Unified API response wrapper
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private T data;
    private String message;

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(200, null, "Success");
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, data, "Success");
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(200, data, message);
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, null, message);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(500, null, message);
    }
}