package com.sidenow.domain.boardType.caution.comment.exception;

import static com.sidenow.domain.boardType.caution.comment.exception.constant.CautionCommentExceptionList.CAUTION_COMMENT_ID_NOT_FOUND_ERROR;

public class CautionCommentIdNotFoundException extends CautionCommentException {
    public CautionCommentIdNotFoundException() {
        super(CAUTION_COMMENT_ID_NOT_FOUND_ERROR.getErrorCode(),
                CAUTION_COMMENT_ID_NOT_FOUND_ERROR.getHttpStatus(),
                CAUTION_COMMENT_ID_NOT_FOUND_ERROR.getMessage());
    }
}
