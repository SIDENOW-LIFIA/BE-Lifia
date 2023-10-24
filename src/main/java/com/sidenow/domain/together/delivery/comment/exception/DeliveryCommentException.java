package com.sidenow.domain.together.delivery.comment.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

abstract class DeliveryCommentException extends ApplicationException {
    DeliveryCommentException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
