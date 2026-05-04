package com.aigrowth.user.repository;

import com.aigrowth.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * User repository using MyBatis Plus
 */
@Mapper
public interface UserRepository extends BaseMapper<User> {
}