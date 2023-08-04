package com.sidenow.domain.member.exception;

import static com.sidenow.domain.member.constant.MemberConstant.MemberExceptionList.*;
public class NotFoundMemberException extends MemberException{
    public NotFoundMemberException() {
        super(NOT_HAVE_EMAIL_ERROR.getErrorCode(),
                NOT_HAVE_EMAIL_ERROR.getHttpStatus(),
                NOT_HAVE_EMAIL_ERROR.getMessage());
    }
}
