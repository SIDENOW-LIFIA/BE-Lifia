package com.sidenow.domain.member.exception;

import static com.sidenow.domain.member.exception.constant.MemberExceptionList.MEMBER_NICKNAME_ALREADY_EXIST_ERROR;

public class MemberNicknameException extends MemberException{
    public MemberNicknameException() {
        super(MEMBER_NICKNAME_ALREADY_EXIST_ERROR.getErrorCode(),
                MEMBER_NICKNAME_ALREADY_EXIST_ERROR.getHttpStatus(),
                MEMBER_NICKNAME_ALREADY_EXIST_ERROR.getMessage());
    }
}
