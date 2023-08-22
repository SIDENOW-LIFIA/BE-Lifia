package com.sidenow.global.config.jwt.exception;

import static com.sidenow.global.config.jwt.constant.JwtContants.JWTExceptionList.ILLEGAL_TOKEN;

public class IllegalException extends JwtException {
    public IllegalException(){
        super(
                ILLEGAL_TOKEN.getErrorCode(),
                ILLEGAL_TOKEN.getHttpStatus(),
                ILLEGAL_TOKEN.getMessage()
        );
    }
}
