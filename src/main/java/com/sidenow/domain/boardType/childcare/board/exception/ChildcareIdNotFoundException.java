package com.sidenow.domain.boardType.childcare.board.exception;

import static com.sidenow.domain.boardType.childcare.board.exception.constant.ChildcareExceptionList.CHILDCARE_ID_NOT_FOUND_ERROR;

public class ChildcareIdNotFoundException extends ChildcareException {
    public ChildcareIdNotFoundException() {
        super(CHILDCARE_ID_NOT_FOUND_ERROR.getErrorCode(),
                CHILDCARE_ID_NOT_FOUND_ERROR.getHttpStatus(),
                CHILDCARE_ID_NOT_FOUND_ERROR.getMessage());
    }
}
