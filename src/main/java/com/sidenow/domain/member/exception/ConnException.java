package com.sidenow.domain.member.exception;


import static com.sidenow.domain.member.constant.MemberConstant.MemberExceptionList.CONNECTION_ERROR;

public class ConnException extends MemberException{

    public ConnException() {
        super(CONNECTION_ERROR.getErrorCode(),
                CONNECTION_ERROR.getHttpStatus(),
                CONNECTION_ERROR.getMessage());
    }
}
