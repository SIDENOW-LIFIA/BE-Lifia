package com.sidenow.domain.member.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberExceptionList {

    MEMBER_NOT_EXIST_ERROR("M0001", HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    MEMBER_EMAIL_ALREADY_EXIST_ERROR("M0002", HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    MEMBER_NICKNAME_ALREADY_EXIST_ERROR("M0003", HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
    MEMBER_EMAIL_NOT_FOUND_ERROR("M0004", HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다."),
    MEMBER_EMAIL_AUTH_CODE_ERROR("M0005", HttpStatus.BAD_REQUEST, "이메일 인증코드가 일치하지 않습니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
