package com.sidenow.domain.member.exception;

import static com.sidenow.domain.member.exception.constant.MemberExceptionList.MEMBER_EMAIL_AUTH_CODE_ERROR;

public class MemberEmailAuthCodeException extends MemberException{
    public MemberEmailAuthCodeException() {
        super(MEMBER_EMAIL_AUTH_CODE_ERROR.getErrorCode(),
                MEMBER_EMAIL_AUTH_CODE_ERROR.getHttpStatus(),
                MEMBER_EMAIL_AUTH_CODE_ERROR.getMessage());
    }
}
