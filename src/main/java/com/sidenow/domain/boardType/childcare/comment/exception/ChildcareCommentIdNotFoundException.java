package com.sidenow.domain.boardType.childcare.comment.exception;

import static com.sidenow.domain.boardType.childcare.comment.exception.constant.ChildcareCommentExceptionList.CHILDCARE_COMMENT_ID_NOT_FOUND_ERROR;

public class ChildcareCommentIdNotFoundException extends ChildcareCommentException {
    public ChildcareCommentIdNotFoundException() {
        super(CHILDCARE_COMMENT_ID_NOT_FOUND_ERROR.getErrorCode(),
                CHILDCARE_COMMENT_ID_NOT_FOUND_ERROR.getHttpStatus(),
                CHILDCARE_COMMENT_ID_NOT_FOUND_ERROR.getMessage());
    }
}
