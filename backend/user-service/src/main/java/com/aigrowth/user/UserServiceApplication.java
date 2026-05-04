package com.aigrowth.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * User service application entry point
 */
@SpringBootApplication(scanBasePackages = {"com.aigrowth.user", "com.aigrowth.common"})
@MapperScan("com.aigrowth.user.repository")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}