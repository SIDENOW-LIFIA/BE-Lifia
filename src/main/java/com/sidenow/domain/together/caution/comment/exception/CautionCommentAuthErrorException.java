package com.sidenow.domain.together.caution.comment.exception;

import static com.sidenow.domain.together.caution.comment.exception.constant.CautionCommentExceptionList.CAUTION_COMMENT_AUTH_ERROR;

public class CautionCommentAuthErrorException extends CautionCommentException {
    public CautionCommentAuthErrorException() {
        super(CAUTION_COMMENT_AUTH_ERROR.getErrorCode(),
                CAUTION_COMMENT_AUTH_ERROR.getHttpStatus(),
                CAUTION_COMMENT_AUTH_ERROR.getMessage());
    }
}
