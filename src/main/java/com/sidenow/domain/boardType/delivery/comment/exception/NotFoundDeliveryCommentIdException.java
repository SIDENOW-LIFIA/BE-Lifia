package com.sidenow.domain.boardType.delivery.comment.exception;

import static com.sidenow.domain.boardType.delivery.comment.exception.constant.DeliveryCommentExceptionList.DELIVERY_COMMENT_ID_NOT_FOUND_ERROR;

public class NotFoundDeliveryCommentIdException extends DeliveryCommentException {
    public NotFoundDeliveryCommentIdException() {
        super(DELIVERY_COMMENT_ID_NOT_FOUND_ERROR.getErrorCode(),
                DELIVERY_COMMENT_ID_NOT_FOUND_ERROR.getHttpStatus(),
                DELIVERY_COMMENT_ID_NOT_FOUND_ERROR.getMessage());
    }
}
