package com.sidenow.domain.boardType.free.comment.exception;

import static com.sidenow.domain.boardType.free.comment.exception.constant.FreeBoardCommentExceptionList.FREE_BOARD_COMMENT_AUTH_ERROR;

public class FreeBoardCommentAuthErrorException extends FreeBoardCommentException{
    public FreeBoardCommentAuthErrorException() {
        super(FREE_BOARD_COMMENT_AUTH_ERROR.getErrorCode(),
                FREE_BOARD_COMMENT_AUTH_ERROR.getHttpStatus(),
                FREE_BOARD_COMMENT_AUTH_ERROR.getMessage());
    }
}
