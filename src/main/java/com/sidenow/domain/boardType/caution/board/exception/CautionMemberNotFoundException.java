package com.sidenow.domain.boardType.caution.board.exception;

import static com.sidenow.domain.boardType.caution.board.exception.constant.CautionExceptionList.CAUTION_MEMBER_NOT_FOUND_ERROR;

public class CautionMemberNotFoundException extends CautionException {
    public CautionMemberNotFoundException() {
        super(CAUTION_MEMBER_NOT_FOUND_ERROR.getErrorCode(),
                CAUTION_MEMBER_NOT_FOUND_ERROR.getHttpStatus(),
                CAUTION_MEMBER_NOT_FOUND_ERROR.getMessage());
    }
}
