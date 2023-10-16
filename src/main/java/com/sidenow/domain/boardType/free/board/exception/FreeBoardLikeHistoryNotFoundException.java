package com.sidenow.domain.boardType.free.board.exception;

import static com.sidenow.domain.boardType.free.board.exception.constant.FreeBoardExceptionList.FREE_BOARD_LIKE_HISTORY_NOT_FOUND_ERROR;

public class FreeBoardLikeHistoryNotFoundException extends FreeBoardException{
    public FreeBoardLikeHistoryNotFoundException() {
        super(FREE_BOARD_LIKE_HISTORY_NOT_FOUND_ERROR.getErrorCode(),
                FREE_BOARD_LIKE_HISTORY_NOT_FOUND_ERROR.getHttpStatus(),
                FREE_BOARD_LIKE_HISTORY_NOT_FOUND_ERROR.getMessage());
    }
}
