package com.sidenow.global.config.security.exception;

import static com.sidenow.global.config.security.constants.SecurityConstants.SecurityExceptionList.LOGGED_IN_NOT_FOUND;

public class LoggedInMemberNotFoundException extends SecurityException{
    public LoggedInMemberNotFoundException() {
        super(LOGGED_IN_NOT_FOUND.getErrorCode(),
                LOGGED_IN_NOT_FOUND.getHttpStatus(),
                LOGGED_IN_NOT_FOUND.getMessage());
    }
}
