package com.sidenow.global.config.redis.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class RedisConstants {

    @Getter
    @RequiredArgsConstructor
    public enum ERefreshTokenMessage{
        TOKEN_REFRESH_SUCCESS("토큰 재발급을 완료하였습니다.");
        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    public enum RefreshTokenExceptionList{
        NOT_FOUND_TOKEN_ERROR("R0003", HttpStatus.NOT_FOUND, "Refresh-Token을 찾을 수 없습니다. 새롭게 로그인을 하세요.");
        private final String errorCode;
        private final HttpStatus httpStatus;
        private final String message;
    }
}