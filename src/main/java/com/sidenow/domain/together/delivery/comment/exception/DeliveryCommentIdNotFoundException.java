package com.sidenow.domain.together.delivery.comment.exception;

import static com.sidenow.domain.together.delivery.comment.exception.constant.DeliveryCommentExceptionList.DELIVERY_COMMENT_ID_NOT_FOUND_ERROR;

public class DeliveryCommentIdNotFoundException extends DeliveryCommentException {
    public DeliveryCommentIdNotFoundException() {
        super(DELIVERY_COMMENT_ID_NOT_FOUND_ERROR.getErrorCode(),
                DELIVERY_COMMENT_ID_NOT_FOUND_ERROR.getHttpStatus(),
                DELIVERY_COMMENT_ID_NOT_FOUND_ERROR.getMessage());
    }
}
