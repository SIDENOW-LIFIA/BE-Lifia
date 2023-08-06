package com.sidenow.global.config.security.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class SecurityException extends ApplicationException {
    protected SecurityException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
