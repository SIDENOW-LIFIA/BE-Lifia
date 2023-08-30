package com.sidenow.global.config.redis.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class RedisConstants {

    @Getter
    @RequiredArgsConstructor
    public enum RedisExceptionList {

        NO_SUCH_REFRESH_MESSAGE("R0001", HttpStatus.NOT_FOUND, "해당 토큰이 저장되어있지 않습니다.");
        private final String errorCode;
        private final HttpStatus httpStatus;
        private final String message;

    }

}
