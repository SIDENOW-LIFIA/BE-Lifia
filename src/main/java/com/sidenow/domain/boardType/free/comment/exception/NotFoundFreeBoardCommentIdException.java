package com.sidenow.domain.boardType.free.comment.exception;

import static com.sidenow.domain.boardType.free.comment.exception.constant.FreeBoardCommentExceptionList.FREE_BOARD_COMMENT_ID_NOT_FOUND_ERROR;

public class NotFoundFreeBoardCommentIdException extends FreeBoardCommentException{
    public NotFoundFreeBoardCommentIdException() {
        super(FREE_BOARD_COMMENT_ID_NOT_FOUND_ERROR.getErrorCode(),
                FREE_BOARD_COMMENT_ID_NOT_FOUND_ERROR.getHttpStatus(),
                FREE_BOARD_COMMENT_ID_NOT_FOUND_ERROR.getMessage());
    }
}
