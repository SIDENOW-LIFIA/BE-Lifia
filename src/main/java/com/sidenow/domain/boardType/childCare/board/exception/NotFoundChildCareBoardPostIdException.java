package com.sidenow.domain.boardType.childCare.board.exception;

import static com.sidenow.domain.boardType.childCare.board.exception.constant.ChildCareBoardExceptionList.NOT_FOUND_CHILDCARE_BOARD_POST_ID_ERROR;

public class NotFoundChildCareBoardPostIdException extends ChildCareBoardException{
    public NotFoundChildCareBoardPostIdException() {
        super(NOT_FOUND_CHILDCARE_BOARD_POST_ID_ERROR.getErrorCode(),
                NOT_FOUND_CHILDCARE_BOARD_POST_ID_ERROR.getHttpStatus(),
                NOT_FOUND_CHILDCARE_BOARD_POST_ID_ERROR.getMessage());
    }
}
