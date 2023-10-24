package com.sidenow.domain.boardType.caution.board.exception;

import static com.sidenow.domain.boardType.caution.board.exception.constant.CautionExceptionList.CAUTION_ID_NOT_FOUND_ERROR;

public class CautionIdNotFoundException extends CautionException {
    public CautionIdNotFoundException() {
        super(CAUTION_ID_NOT_FOUND_ERROR.getErrorCode(),
                CAUTION_ID_NOT_FOUND_ERROR.getHttpStatus(),
                CAUTION_ID_NOT_FOUND_ERROR.getMessage());
    }
}
