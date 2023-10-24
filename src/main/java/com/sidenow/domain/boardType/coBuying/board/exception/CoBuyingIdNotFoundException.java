package com.sidenow.domain.boardType.coBuying.board.exception;

import static com.sidenow.domain.boardType.coBuying.board.exception.constant.CoBuyingExceptionList.CO_BUYING_ID_NOT_FOUND_ERROR;

public class CoBuyingIdNotFoundException extends CoBuyingException {
    public CoBuyingIdNotFoundException() {
        super(CO_BUYING_ID_NOT_FOUND_ERROR.getErrorCode(),
                CO_BUYING_ID_NOT_FOUND_ERROR.getHttpStatus(),
                CO_BUYING_ID_NOT_FOUND_ERROR.getMessage());
    }
}
