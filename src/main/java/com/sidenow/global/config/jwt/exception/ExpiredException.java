package com.sidenow.global.config.jwt.exception;

import static com.sidenow.global.config.jwt.constant.JwtContants.JWTExceptionList.EXPIRED_TOKEN;

public class ExpiredException extends JwtException{
    public ExpiredException(){
        super(EXPIRED_TOKEN.getErrorCode(),
                EXPIRED_TOKEN.getHttpStatus(),
                EXPIRED_TOKEN.getMessage()
        );
    }
}
