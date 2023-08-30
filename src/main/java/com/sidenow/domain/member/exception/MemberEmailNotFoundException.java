package com.sidenow.domain.member.exception;

import static com.sidenow.domain.member.exception.constant.MemberExceptionList.MEMBER_EMAIL_NOT_FOUND_ERROR;

public class MemberEmailNotFoundException extends MemberException{
    public MemberEmailNotFoundException() {
        super(MEMBER_EMAIL_NOT_FOUND_ERROR.getErrorCode(),
                MEMBER_EMAIL_NOT_FOUND_ERROR.getHttpStatus(),
                MEMBER_EMAIL_NOT_FOUND_ERROR.getMessage());
    }
}
