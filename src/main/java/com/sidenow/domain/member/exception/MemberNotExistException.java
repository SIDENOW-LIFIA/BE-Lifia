package com.sidenow.domain.member.exception;

import static com.sidenow.domain.member.constant.MemberConstant.MemberExceptionList.*;

public class NotFoundEmailException extends MemberException {
    public NotFoundEmailException() {
        super(NOT_FOUND_MEMBER_ERROR.getErrorCode(),
                NOT_FOUND_MEMBER_ERROR.getHttpStatus(),
                NOT_FOUND_MEMBER_ERROR.getMessage());
    }
}
