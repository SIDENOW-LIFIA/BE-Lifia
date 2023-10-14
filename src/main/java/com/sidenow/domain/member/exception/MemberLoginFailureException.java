package com.sidenow.domain.member.exception;

import static com.sidenow.domain.member.exception.constant.MemberExceptionList.MEMBER_LOGIN_FAILURE_ERROR;

public class MemberLoginFailureException extends MemberException{
    public MemberLoginFailureException() {
        super(MEMBER_LOGIN_FAILURE_ERROR.getErrorCode(),
                MEMBER_LOGIN_FAILURE_ERROR.getHttpStatus(),
                MEMBER_LOGIN_FAILURE_ERROR.getMessage());
    }
}
