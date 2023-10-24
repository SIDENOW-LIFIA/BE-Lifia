package com.sidenow.domain.boardType.delivery.comment.exception;

import static com.sidenow.domain.boardType.delivery.comment.exception.constant.DeliveryCommentExceptionList.DELIVERY_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR;

public class DeliveryCommentLikeHistoryNotFoundException extends DeliveryCommentException {
    public DeliveryCommentLikeHistoryNotFoundException() {
        super(DELIVERY_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getErrorCode(),
                DELIVERY_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getHttpStatus(),
                DELIVERY_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getMessage());
    }
}
