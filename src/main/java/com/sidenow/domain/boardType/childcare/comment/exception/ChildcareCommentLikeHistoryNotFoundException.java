package com.sidenow.domain.boardType.childcare.comment.exception;

import static com.sidenow.domain.boardType.childcare.comment.exception.constant.ChildcareCommentExceptionList.CHILDCARE_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR;

public class ChildcareCommentLikeHistoryNotFoundException extends ChildcareCommentException {
    public ChildcareCommentLikeHistoryNotFoundException() {
        super(CHILDCARE_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getErrorCode(),
                CHILDCARE_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getHttpStatus(),
                CHILDCARE_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getMessage());
    }
}
