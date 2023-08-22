package com.sidenow.global.config.redis.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class RefreshTokenException extends ApplicationException {
    protected RefreshTokenException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
