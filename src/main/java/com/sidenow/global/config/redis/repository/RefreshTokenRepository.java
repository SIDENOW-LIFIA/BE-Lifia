package com.sidenow.global.config.redis.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenRepository {
    private RedisTemplate redisTemplate;

    @Value()
}
