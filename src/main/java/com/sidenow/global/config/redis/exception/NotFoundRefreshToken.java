package com.sidenow.global.config.redis.exception;

import static com.sidenow.global.config.redis.constant.RedisConstants.RefreshTokenExceptionList.NOT_FOUND_TOKEN_ERROR;

public class NotFoundRefreshToken extends RefreshTokenException{
    public NotFoundRefreshToken(){
        super(NOT_FOUND_TOKEN_ERROR.getErrorCode(),
                NOT_FOUND_TOKEN_ERROR.getHttpStatus(),
                NOT_FOUND_TOKEN_ERROR.getMessage());
    }

}
