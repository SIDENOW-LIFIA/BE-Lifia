package com.sidenow.domain.boardType.free.board.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class FreeBoardException extends ApplicationException {
    protected FreeBoardException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
