package com.sidenow.domain.member.exception;

import static com.sidenow.domain.member.exception.constant.MemberExceptionList.MEMBER_NOT_LOGIN;

public class MemberNotLoginException extends MemberException{
    public MemberNotLoginException() {
        super(MEMBER_NOT_LOGIN.getErrorCode(),
                MEMBER_NOT_LOGIN.getHttpStatus(),
                MEMBER_NOT_LOGIN.getMessage());
    }
}
