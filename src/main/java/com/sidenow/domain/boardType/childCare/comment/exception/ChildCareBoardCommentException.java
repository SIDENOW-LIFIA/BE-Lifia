package com.sidenow.domain.boardType.childCare.comment.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class ChildCareBoardCommentException extends ApplicationException {
    protected ChildCareBoardCommentException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
