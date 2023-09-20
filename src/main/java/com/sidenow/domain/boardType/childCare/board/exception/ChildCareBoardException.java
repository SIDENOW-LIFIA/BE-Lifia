package com.sidenow.domain.boardType.childCare.board.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class ChildCareBoardException extends ApplicationException {
    protected ChildCareBoardException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
