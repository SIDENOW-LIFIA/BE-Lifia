package com.sidenow.global.config.security.exception;
import static com.sidenow.global.config.security.constants.SecurityConstants.SecurityExceptionList.NOT_AUTH_WITH_OAUTH2;

public class MemberNotAuthenticatedWithOAuth2Exception extends SecurityException{

    public MemberNotAuthenticatedWithOAuth2Exception() {
        super(NOT_AUTH_WITH_OAUTH2.getErrorCode(),
                NOT_AUTH_WITH_OAUTH2.getHttpStatus(),
                NOT_AUTH_WITH_OAUTH2.getMessage());
    }

}
