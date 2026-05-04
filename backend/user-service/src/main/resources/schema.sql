-- Database initialization script for AI Growth Partner
CREATE DATABASE IF NOT EXISTS aigrowth DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE aigrowth;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'User ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT 'Username',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT 'Email',
    password VARCHAR(100) NOT NULL COMMENT 'Password (MD5 hashed)',
    nickname VARCHAR(50) DEFAULT NULL COMMENT 'Display name',
    avatar VARCHAR(255) DEFAULT NULL COMMENT 'Avatar URL',
    status TINYINT DEFAULT 1 COMMENT 'Status: 0-disabled, 1-enabled',
    level INT DEFAULT 1 COMMENT 'User level',
    xp BIGINT DEFAULT 0 COMMENT 'Experience points',
    bio TEXT COMMENT 'User biography',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    deleted TINYINT DEFAULT 0 COMMENT 'Soft delete: 0-not deleted, 1-deleted',
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='User table';