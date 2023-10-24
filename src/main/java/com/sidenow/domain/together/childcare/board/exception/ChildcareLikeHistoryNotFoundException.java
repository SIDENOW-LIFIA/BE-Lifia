package com.sidenow.domain.together.childcare.board.exception;

import static com.sidenow.domain.together.childcare.board.exception.constant.ChildcareExceptionList.CHILDCARE_LIKE_HISTORY_NOT_FOUND_ERROR;

public class ChildcareLikeHistoryNotFoundException extends ChildcareException {
    public ChildcareLikeHistoryNotFoundException() {
        super(CHILDCARE_LIKE_HISTORY_NOT_FOUND_ERROR.getErrorCode(),
                CHILDCARE_LIKE_HISTORY_NOT_FOUND_ERROR.getHttpStatus(),
                CHILDCARE_LIKE_HISTORY_NOT_FOUND_ERROR.getMessage());
    }
}
