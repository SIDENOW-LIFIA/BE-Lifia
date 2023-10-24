package com.sidenow.domain.together.childcare.comment.exception;

import static com.sidenow.domain.together.childcare.comment.exception.constant.ChildcareCommentExceptionList.CHILDCARE_COMMENT_AUTH_ERROR;

public class ChildcareCommentAuthErrorException extends ChildcareCommentException {
    public ChildcareCommentAuthErrorException() {
        super(CHILDCARE_COMMENT_AUTH_ERROR.getErrorCode(),
                CHILDCARE_COMMENT_AUTH_ERROR.getHttpStatus(),
                CHILDCARE_COMMENT_AUTH_ERROR.getMessage());
    }
}
