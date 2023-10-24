package com.sidenow.domain.together.delivery.board.exception;

import static com.sidenow.domain.together.delivery.board.exception.constant.DeliveryExceptionList.DELIVERY_MEMBER_NOT_FOUND_ERROR;

public class DeliveryMemberNotFoundException extends DeliveryException {
    public DeliveryMemberNotFoundException() {
        super(DELIVERY_MEMBER_NOT_FOUND_ERROR.getErrorCode(),
                DELIVERY_MEMBER_NOT_FOUND_ERROR.getHttpStatus(),
                DELIVERY_MEMBER_NOT_FOUND_ERROR.getMessage());
    }
}
