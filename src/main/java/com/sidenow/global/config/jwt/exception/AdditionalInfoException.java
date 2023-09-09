package com.sidenow.global.config.jwt.exception;

import static com.sidenow.global.config.jwt.constant.JwtContants.JWTExceptionList.ADDITIONAL_REQUIRED_TOKEN;

public class AdditionalInfoException extends JwtException{
    public AdditionalInfoException(){
        super(ADDITIONAL_REQUIRED_TOKEN.getErrorCode(),
                ADDITIONAL_REQUIRED_TOKEN.getHttpStatus(),
                ADDITIONAL_REQUIRED_TOKEN.getMessage());
    }
}
