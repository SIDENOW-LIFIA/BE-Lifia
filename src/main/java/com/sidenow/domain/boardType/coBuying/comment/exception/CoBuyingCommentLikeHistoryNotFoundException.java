package com.sidenow.domain.boardType.coBuying.comment.exception;

import static com.sidenow.domain.boardType.coBuying.comment.exception.constant.CoBuyingCommentExceptionList.CO_BUYING_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR;

public class CoBuyingCommentLikeHistoryNotFoundException extends CoBuyingCommentException {
    public CoBuyingCommentLikeHistoryNotFoundException() {
        super(CO_BUYING_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getErrorCode(),
                CO_BUYING_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getHttpStatus(),
                CO_BUYING_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getMessage());
    }
}
