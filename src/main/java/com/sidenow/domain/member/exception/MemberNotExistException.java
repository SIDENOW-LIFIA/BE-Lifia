package com.sidenow.domain.member.exception;

import static com.sidenow.domain.member.exception.constant.MemberExceptionList.MEMBER_NOT_EXIST_ERROR;

public class MemberNotExistException extends MemberException {
    public MemberNotExistException() {
        super(MEMBER_NOT_EXIST_ERROR.getErrorCode(),
                MEMBER_NOT_EXIST_ERROR.getHttpStatus(),
                MEMBER_NOT_EXIST_ERROR.getMessage());
    }
}
