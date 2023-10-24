package com.sidenow.domain.together.delivery.board.exception;

import static com.sidenow.domain.together.delivery.board.exception.constant.DeliveryExceptionList.DELIVERY_LIKE_HISTORY_NOT_FOUND_ERROR;

public class DeliveryLikeHistoryNotFoundException extends DeliveryException {
    public DeliveryLikeHistoryNotFoundException() {
        super(DELIVERY_LIKE_HISTORY_NOT_FOUND_ERROR.getErrorCode(),
                DELIVERY_LIKE_HISTORY_NOT_FOUND_ERROR.getHttpStatus(),
                DELIVERY_LIKE_HISTORY_NOT_FOUND_ERROR.getMessage());
    }
}
