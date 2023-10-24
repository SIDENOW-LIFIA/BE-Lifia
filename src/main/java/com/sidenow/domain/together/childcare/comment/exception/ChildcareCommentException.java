package com.sidenow.domain.together.childcare.comment.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

abstract class ChildcareCommentException extends ApplicationException {
    ChildcareCommentException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
