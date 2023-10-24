package com.sidenow.domain.freeboard.board.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

abstract class FreeBoardException extends ApplicationException {
    FreeBoardException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
