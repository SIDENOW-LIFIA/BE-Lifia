package com.sidenow.domain.boardType.childCare.comment.exception;

import static com.sidenow.domain.boardType.childCare.comment.exception.constant.ChildCareBoardCommentExceptionList.NOT_FOUND_CHILDCARE_BOARD_COMMENT_ID_ERROR;

public class NotFoundChildCareBoardCommentIdException extends ChildCareBoardCommentException{
    public NotFoundChildCareBoardCommentIdException() {
        super(NOT_FOUND_CHILDCARE_BOARD_COMMENT_ID_ERROR.getErrorCode(),
                NOT_FOUND_CHILDCARE_BOARD_COMMENT_ID_ERROR.getHttpStatus(),
                NOT_FOUND_CHILDCARE_BOARD_COMMENT_ID_ERROR.getMessage());
    }
}
