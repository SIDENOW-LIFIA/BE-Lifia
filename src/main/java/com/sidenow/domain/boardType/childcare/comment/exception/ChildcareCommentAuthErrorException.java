package com.sidenow.domain.boardType.childcare.comment.exception;

import static com.sidenow.domain.boardType.childcare.comment.exception.constant.ChildcareCommentExceptionList.CHILDCARE_COMMENT_AUTH_ERROR;

public class ChildcareCommentAuthErrorException extends ChildcareCommentException {
    public ChildcareCommentAuthErrorException() {
        super(CHILDCARE_COMMENT_AUTH_ERROR.getErrorCode(),
                CHILDCARE_COMMENT_AUTH_ERROR.getHttpStatus(),
                CHILDCARE_COMMENT_AUTH_ERROR.getMessage());
    }
}
