package com.sidenow.global.exception;

import org.springframework.http.HttpStatus;

public class NoExistMemberException extends LifiaException{
    private static final String MESSAGE = "존재하지 않는 유저 이메일입니다.";

    public NoExistMemberException() {
        super(MESSAGE, HttpStatus.NO_CONTENT);
    }
}
