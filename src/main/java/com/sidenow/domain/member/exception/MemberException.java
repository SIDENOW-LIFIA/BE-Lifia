package com.sidenow.domain.member.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

abstract class MemberException extends ApplicationException {
    MemberException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
