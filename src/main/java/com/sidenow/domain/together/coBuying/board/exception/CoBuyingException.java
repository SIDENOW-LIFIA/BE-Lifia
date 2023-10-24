package com.sidenow.domain.together.coBuying.board.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

abstract class CoBuyingException extends ApplicationException {
    CoBuyingException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
