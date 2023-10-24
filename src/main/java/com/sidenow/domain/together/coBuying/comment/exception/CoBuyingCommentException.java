package com.sidenow.domain.together.coBuying.comment.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

abstract class CoBuyingCommentException extends ApplicationException {
    CoBuyingCommentException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
