package com.sidenow.domain.together.caution.board.exception;

import static com.sidenow.domain.together.caution.board.exception.constant.CautionExceptionList.CAUTION_LIKE_HISTORY_NOT_FOUND_ERROR;

public class CautionLikeHistoryNotFoundException extends CautionException {
    public CautionLikeHistoryNotFoundException() {
        super(CAUTION_LIKE_HISTORY_NOT_FOUND_ERROR.getErrorCode(),
                CAUTION_LIKE_HISTORY_NOT_FOUND_ERROR.getHttpStatus(),
                CAUTION_LIKE_HISTORY_NOT_FOUND_ERROR.getMessage());
    }
}
