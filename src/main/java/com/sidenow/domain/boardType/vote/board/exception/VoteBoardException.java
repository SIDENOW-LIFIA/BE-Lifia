package com.sidenow.domain.boardType.vote.board.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class VoteBoardException extends ApplicationException {
    protected VoteBoardException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
