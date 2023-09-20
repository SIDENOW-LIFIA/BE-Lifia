package com.sidenow.domain.boardType.childCare.comment.exception;

import static com.sidenow.domain.boardType.childCare.comment.exception.constant.ChildCareBoardCommentExceptionList.CHILDCARE_BOARD_COMMENT_AUTH_ERROR;

public class ChildCareBoardCommentAuthErrorException extends ChildCareBoardCommentException{
    public ChildCareBoardCommentAuthErrorException() {
        super(CHILDCARE_BOARD_COMMENT_AUTH_ERROR.getErrorCode(),
                CHILDCARE_BOARD_COMMENT_AUTH_ERROR.getHttpStatus(),
                CHILDCARE_BOARD_COMMENT_AUTH_ERROR.getMessage());
    }
}
