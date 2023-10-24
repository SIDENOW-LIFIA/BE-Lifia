package com.sidenow.domain.together.delivery.comment.exception;

import static com.sidenow.domain.together.delivery.comment.exception.constant.DeliveryCommentExceptionList.DELIVERY_COMMENT_AUTH_ERROR;

public class DeliveryCommentAuthErrorException extends DeliveryCommentException {
    public DeliveryCommentAuthErrorException() {
        super(DELIVERY_COMMENT_AUTH_ERROR.getErrorCode(),
                DELIVERY_COMMENT_AUTH_ERROR.getHttpStatus(),
                DELIVERY_COMMENT_AUTH_ERROR.getMessage());
    }
}
