package com.sidenow.domain.boardType.delivery.board.exception;

import static com.sidenow.domain.boardType.delivery.board.exception.constant.DeliveryExceptionList.DELIVERY_ID_NOT_FOUND_ERROR;

public class DeliveryIdNotFoundException extends DeliveryException {
    public DeliveryIdNotFoundException() {
        super(DELIVERY_ID_NOT_FOUND_ERROR.getErrorCode(),
                DELIVERY_ID_NOT_FOUND_ERROR.getHttpStatus(),
                DELIVERY_ID_NOT_FOUND_ERROR.getMessage());
    }
}
