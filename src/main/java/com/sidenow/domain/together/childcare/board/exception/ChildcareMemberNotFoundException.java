package com.sidenow.domain.together.childcare.board.exception;

import static com.sidenow.domain.together.childcare.board.exception.constant.ChildcareExceptionList.CHILDCARE_MEMBER_NOT_FOUND_ERROR;

public class ChildcareMemberNotFoundException extends ChildcareException {
    public ChildcareMemberNotFoundException() {
        super(CHILDCARE_MEMBER_NOT_FOUND_ERROR.getErrorCode(),
                CHILDCARE_MEMBER_NOT_FOUND_ERROR.getHttpStatus(),
                CHILDCARE_MEMBER_NOT_FOUND_ERROR.getMessage());
    }
}
