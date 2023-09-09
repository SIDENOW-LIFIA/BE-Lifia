package com.sidenow.domain.member.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class MemberConstant {

    @Getter
    @RequiredArgsConstructor
    public enum MemberSuccessMessage {
        MEMBER_LOGIN_SUCCESS("로그인에 성공했습니다."),
        MEMBER_SIGN_UP_SUCCESS("회원 가입에 성공했습니다."),
        MEMBER_CHECK_EMAIL_DUPLICATE_SUCCESS("사용 가능한 이메일입니다."),
        MEMBER_CHECK_NICKNAME_DUPLICATE_SUCCESS("사용 가능한 닉네임입니다."),
        MEMBER_DELETE_SUCCESS("회원 탈퇴를 하였습니다."),
        MEMBER_LOGOUT_SUCCESS("로그아웃을 하였습니다."),
        MEMBER_MY_PAGE_MODIFY_SUCCESS("마이페이지가 성공적으로 수정되었습니다."),
        MEMBER_SEND_EMAIL_AUTH_CODE_SUCCESS("이메일 인증코드를 전송하였습니다."),
        MEMBER_EMAIL_AUTH_CODE_VERIFIED("이메일 인증코드가 일치합니다."),
        MEMBER_TOKEN_REFRESH_SUCCESS("토큰 재발급을 완료하였습니다.");
        private final String message;
    }

    @Getter
    public enum Role {
        Role_USER, ROLE_ADMIN
    }

    @Getter
    public enum Provider {
        GOOGLE, KAKAO, NAVER
    }


    @Getter
    @RequiredArgsConstructor
    public enum Process {
        SIGN_UP_ING("회원가입 중 - 추가 정보를 입력해주세요."),
        SIGN_UP_SUCCESS("회원가입이 완료되었습니다."),
        LOGIN_SUCCESS("로그인이 완료되었습니다.");

        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    public enum MemberExceptionList {
        CONNECTION_ERROR("U0001", HttpStatus.UNAUTHORIZED, "AccessToken 값이 잘못되었습니다."),
        NOT_HAVE_EMAIL_ERROR("U0002", HttpStatus.NOT_FOUND, "해당 이메일로 User를 찾을 수 없습니다."),
        NOT_FOUND_MEMBER_ERROR("U0003", HttpStatus.NOT_FOUND, "해당 id로 User를 찾을 수 없습니다.");
        private final String errorCode;
        private final HttpStatus httpStatus;
        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    public enum MemberServiceMessage {
        LOGIN_URL("https://kapi.kakao.com/v2/user/me"),
        LOGOUT_URL("https://kapi.kakao.com/v1/user/logout"),
        DELETE_URL("https://kapi.kakao.com/v1/user/unlink"),
        KAKAO_ACCOUNT("kakao_account"),
        VALID_NICKNAME("사용 가능한 닉네임입니다."),
        EXISTED_NICKNAME("이미 존재하는 닉네임입니다.");
        private final String value;
    }
}
