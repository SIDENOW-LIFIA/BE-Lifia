package com.sidenow.domain.freeboard.board.exception;

import static com.sidenow.domain.freeboard.board.exception.constant.FreeBoardExceptionList.FREE_BOARD_ID_NOT_FOUND_ERROR;

public class FreeBoardIdNotFoundException extends FreeBoardException{
    public FreeBoardIdNotFoundException() {
        super(FREE_BOARD_ID_NOT_FOUND_ERROR.getErrorCode(),
                FREE_BOARD_ID_NOT_FOUND_ERROR.getHttpStatus(),
                FREE_BOARD_ID_NOT_FOUND_ERROR.getMessage());
    }
}
