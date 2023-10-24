package com.sidenow.domain.together.childcare.comment.exception;

import static com.sidenow.domain.together.childcare.comment.exception.constant.ChildcareCommentExceptionList.CHILDCARE_COMMENT_ID_NOT_FOUND_ERROR;

public class ChildcareCommentIdNotFoundException extends ChildcareCommentException {
    public ChildcareCommentIdNotFoundException() {
        super(CHILDCARE_COMMENT_ID_NOT_FOUND_ERROR.getErrorCode(),
                CHILDCARE_COMMENT_ID_NOT_FOUND_ERROR.getHttpStatus(),
                CHILDCARE_COMMENT_ID_NOT_FOUND_ERROR.getMessage());
    }
}
