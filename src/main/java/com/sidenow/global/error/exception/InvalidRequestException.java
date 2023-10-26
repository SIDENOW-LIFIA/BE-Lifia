package com.sidenow.global.error.exception;

import com.sidenow.global.util.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidRequestException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
