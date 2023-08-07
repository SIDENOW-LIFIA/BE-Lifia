package com.sidenow.global.config.jwt.exception;

import static com.sidenow.global.config.jwt.constant.JwtContants.JWTExceptionList.UNSUPPORTED_TOKEN;

public class UnsupportedException extends JwtException {
    public UnsupportedException(){
        super(
                UNSUPPORTED_TOKEN.getErrorCode(),
                UNSUPPORTED_TOKEN.getHttpStatus(),
                UNSUPPORTED_TOKEN.getMessage()
        );
    }
}
