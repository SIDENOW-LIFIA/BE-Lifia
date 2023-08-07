package com.sidenow.global.config.jwt.exception;

import static com.sidenow.global.config.jwt.constant.JwtContants.JWTExceptionList.UNKNOWN_ERROR;

public class UnknownException extends JwtException {
    public UnknownException(){
        super(UNKNOWN_ERROR.getErrorCode(),
                UNKNOWN_ERROR.getHttpStatus(),
                UNKNOWN_ERROR.getMessage());
    }
}
