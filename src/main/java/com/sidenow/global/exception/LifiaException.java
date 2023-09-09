package com.sidenow.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LifiaException extends RuntimeException{
    private final HttpStatus status;

    public LifiaException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }

    public LifiaException(final String message, final Throwable cause, final HttpStatus status) {
        super(message, cause);
        this.status = status;
    }
}
