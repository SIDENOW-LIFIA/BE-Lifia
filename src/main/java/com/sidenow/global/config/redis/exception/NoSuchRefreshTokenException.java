package com.sidenow.global.config.redis.exception;

import static com.sidenow.global.config.redis.constant.RedisConstants.RedisExceptionList.NO_SUCH_REFRESH_MESSAGE;

public class NoSuchRefreshTokenException extends RedisException {
    public NoSuchRefreshTokenException() {
        super(
                NO_SUCH_REFRESH_MESSAGE.getErrorCode(),
                NO_SUCH_REFRESH_MESSAGE.getHttpStatus(),
                NO_SUCH_REFRESH_MESSAGE.getMessage()
        );
    }
}