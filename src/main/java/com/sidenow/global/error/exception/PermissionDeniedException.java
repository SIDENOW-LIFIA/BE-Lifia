package com.sidenow.global.error.exception;

import com.sidenow.global.util.ErrorCode;
import lombok.Getter;

@Getter
public class PermissionDeniedException extends RuntimeException {

    private final ErrorCode errorCode;

    public PermissionDeniedException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
