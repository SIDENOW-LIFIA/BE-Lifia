package com.sidenow.global.config.security.exception;

import static com.sidenow.global.config.security.constants.SecurityConstants.SecurityExceptionList.NO_AUTHENTICATION_FOUND;

public class NoAuthenticationFoundException extends SecurityException{
    public NoAuthenticationFoundException() {
        super(NO_AUTHENTICATION_FOUND.getErrorCode(),
                NO_AUTHENTICATION_FOUND.getHttpStatus(),
                NO_AUTHENTICATION_FOUND.getMessage());
    }
}
