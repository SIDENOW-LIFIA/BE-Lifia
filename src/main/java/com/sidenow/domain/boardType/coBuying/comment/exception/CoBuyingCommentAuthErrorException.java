package com.sidenow.domain.boardType.coBuying.comment.exception;

import static com.sidenow.domain.boardType.coBuying.comment.exception.constant.CoBuyingCommentExceptionList.CO_BUYING_COMMENT_AUTH_ERROR;

public class CoBuyingCommentAuthErrorException extends CoBuyingCommentException {
    public CoBuyingCommentAuthErrorException() {
        super(CO_BUYING_COMMENT_AUTH_ERROR.getErrorCode(),
                CO_BUYING_COMMENT_AUTH_ERROR.getHttpStatus(),
                CO_BUYING_COMMENT_AUTH_ERROR.getMessage());
    }
}
