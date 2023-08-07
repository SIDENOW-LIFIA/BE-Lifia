package com.sidenow.global.config.jwt.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class JwtException extends ApplicationException {
    protected JwtException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
