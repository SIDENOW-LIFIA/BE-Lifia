package com.sidenow.domain.together.caution.comment.exception;

import static com.sidenow.domain.together.caution.comment.exception.constant.CautionCommentExceptionList.CAUTION_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR;

public class CautionCommentLikeHistoryNotFoundException extends CautionCommentException {
    public CautionCommentLikeHistoryNotFoundException() {
        super(CAUTION_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getErrorCode(),
                CAUTION_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getHttpStatus(),
                CAUTION_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getMessage());
    }
}
