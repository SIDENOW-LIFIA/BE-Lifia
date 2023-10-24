package com.sidenow.domain.freeboard.comment.exception;

import static com.sidenow.domain.freeboard.comment.exception.constant.FreeBoardCommentExceptionList.FREE_BOARD_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR;

public class FreeBoardCommentLikeHistoryNotFoundException extends FreeBoardCommentException{
    public FreeBoardCommentLikeHistoryNotFoundException() {
        super(FREE_BOARD_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getErrorCode(),
                FREE_BOARD_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getHttpStatus(),
                FREE_BOARD_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getMessage());
    }
}
