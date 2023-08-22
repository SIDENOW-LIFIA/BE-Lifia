package com.sidenow.domain.boardType.free.comment.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class FreeBoardCommentException extends ApplicationException {
    protected FreeBoardCommentException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
