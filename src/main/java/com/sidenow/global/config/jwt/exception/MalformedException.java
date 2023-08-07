package com.sidenow.global.config.jwt.exception;

import static com.sidenow.global.config.jwt.constant.JwtContants.JWTExceptionList.MAL_FORMED_TOKEN;

public class MalformedException extends JwtException{
    public MalformedException() {
        super(
                MAL_FORMED_TOKEN.getErrorCode(),
                MAL_FORMED_TOKEN.getHttpStatus(),
                MAL_FORMED_TOKEN.getMessage()
        );
    }
}
