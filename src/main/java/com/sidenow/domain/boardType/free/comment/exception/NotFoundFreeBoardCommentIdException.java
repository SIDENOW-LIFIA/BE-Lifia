package com.sidenow.domain.boardType.free.comment.exception;

import static com.sidenow.domain.boardType.free.comment.exception.constant.FreeBoardCommentExceptionList.NOT_FOUND_FREE_BOARD_COMMENT_ID_ERROR;

public class NotFoundFreeBoardCommentIdException extends FreeBoardCommentException{
    public NotFoundFreeBoardCommentIdException() {
        super(NOT_FOUND_FREE_BOARD_COMMENT_ID_ERROR.getErrorCode(),
                NOT_FOUND_FREE_BOARD_COMMENT_ID_ERROR.getHttpStatus(),
                NOT_FOUND_FREE_BOARD_COMMENT_ID_ERROR.getMessage());
    }
}
