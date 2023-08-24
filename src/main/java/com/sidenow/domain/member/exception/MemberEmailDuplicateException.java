package com.sidenow.domain.member.exception;

import static com.sidenow.domain.member.exception.constant.MemberExceptionList.MEMBER_EMAIL_ALREADY_EXIST_ERROR;

public class MemberEmailException extends MemberException{
    public MemberEmailException() {
        super(MEMBER_EMAIL_ALREADY_EXIST_ERROR.getErrorCode(),
                MEMBER_EMAIL_ALREADY_EXIST_ERROR.getHttpStatus(),
                MEMBER_EMAIL_ALREADY_EXIST_ERROR.getMessage());
    }
}
