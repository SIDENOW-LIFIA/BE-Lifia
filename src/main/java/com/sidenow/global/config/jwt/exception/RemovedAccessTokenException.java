package com.sidenow.global.config.jwt.exception;

import static com.sidenow.global.config.jwt.constant.JwtContants.JWTExceptionList.REMOVED_ACCESS_TOKEN;

public class RemovedAccessTokenException extends JwtException {
    public RemovedAccessTokenException(){
        super(
                REMOVED_ACCESS_TOKEN.getErrorCode(),
                REMOVED_ACCESS_TOKEN.getHttpStatus(),
                REMOVED_ACCESS_TOKEN.getMessage()
        );
    }
}
