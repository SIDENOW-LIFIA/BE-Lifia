package com.sidenow.domain.boardType.coBuying.comment.exception;

import static com.sidenow.domain.boardType.coBuying.comment.exception.constant.CoBuyingCommentExceptionList.CO_BUYING_COMMENT_ID_NOT_FOUND_ERROR;

public class CoBuyingCommentIdNotFoundException extends CoBuyingCommentException {
    public CoBuyingCommentIdNotFoundException() {
        super(CO_BUYING_COMMENT_ID_NOT_FOUND_ERROR.getErrorCode(),
                CO_BUYING_COMMENT_ID_NOT_FOUND_ERROR.getHttpStatus(),
                CO_BUYING_COMMENT_ID_NOT_FOUND_ERROR.getMessage());
    }
}
