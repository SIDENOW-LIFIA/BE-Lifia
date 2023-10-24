package com.sidenow.domain.boardType.coBuying.board.exception;

import static com.sidenow.domain.boardType.coBuying.board.exception.constant.CoBuyingExceptionList.CO_BUYING_LIKE_HISTORY_NOT_FOUND_ERROR;

public class CoBuyingLikeHistoryNotFoundException extends CoBuyingException {
    public CoBuyingLikeHistoryNotFoundException() {
        super(CO_BUYING_LIKE_HISTORY_NOT_FOUND_ERROR.getErrorCode(),
                CO_BUYING_LIKE_HISTORY_NOT_FOUND_ERROR.getHttpStatus(),
                CO_BUYING_LIKE_HISTORY_NOT_FOUND_ERROR.getMessage());
    }
}
