package com.sidenow.domain.freeboard.comment.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

abstract class FreeBoardCommentException extends ApplicationException {
    FreeBoardCommentException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
