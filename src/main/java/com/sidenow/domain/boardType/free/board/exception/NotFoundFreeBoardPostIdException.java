package com.sidenow.domain.boardType.free.board.exception;

import static com.sidenow.domain.boardType.free.board.exception.constant.FreeBoardExceptionList.NOT_FOUND_FREE_BOARD_POST_ID_ERROR;

public class NotFoundFreeBoardPostIdException extends FreeBoardException{
    public NotFoundFreeBoardPostIdException() {
        super(NOT_FOUND_FREE_BOARD_POST_ID_ERROR.getErrorCode(),
                NOT_FOUND_FREE_BOARD_POST_ID_ERROR.getHttpStatus(),
                NOT_FOUND_FREE_BOARD_POST_ID_ERROR.getMessage());
    }
}
