package com.sidenow.domain.boardType.childcare.board.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

abstract class ChildcareException extends ApplicationException {
    ChildcareException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
