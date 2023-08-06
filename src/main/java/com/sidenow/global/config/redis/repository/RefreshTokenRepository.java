package com.sidenow.global.config.redis.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class RefreshTokenRepository {
    private RedisTemplate redisTemplate;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidityTime;

    public RefreshTokenRepository(final RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void save(String refreshToken, Long memberId) {
        // 동일한 key 값으로 저장하면 value 값 update됨
        redisTemplate.opsForValue().set(String.valueOf(memberId), refreshToken, refreshTokenValidityTime, TimeUnit.SECONDS);
    }

    public void deleteById(Long memberId) {
        redisTemplate.delete(String.valueOf(memberId));
    }

    public Optional<String> findById(final Long memberId) {
        String refreshToken = (String) redisTemplate.opsForValue().get(String.valueOf(memberId));
        return Optional.ofNullable(refreshToken);
    }
}
