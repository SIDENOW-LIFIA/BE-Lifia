package com.sidenow.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sidenow.global.dto.TokenInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public abstract class MemberDto {

    @Getter
    @AllArgsConstructor
    @Builder
    @Schema(description = "회원가입을 위한 요청 객체")
    @NoArgsConstructor
    public static class SignUpRequest {
        @NotBlank(message = "이메일을 입력하세요.")
        @Email(message = "올바른 이메일주소를 입력하세요.")
        private String email;

        @NotBlank(message = "비밀번호를 입력하세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력하세요.")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private String password;

        @NotBlank(message = "이름을 입력하세요.")
        private String name;

        @NotBlank(message = "닉네임을 입력하세요.")
        @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력하세요.")
        private String nickname;

        @NotBlank(message = "거주지를 선택하세요.")
        private String address;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Schema(description = "카카오 로그인을 위한 요청 객체")
    @NoArgsConstructor
    public static class LoginRequest {
        @NotBlank(message = "카카오 액세스 토큰을 입력해주세요.")
        @Schema(description = "카카오 accessToken을 주세요.")
        private String token;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Schema(description = "회원 탈퇴를 위한 요청 객체")
    @NoArgsConstructor
    public static class DeleteAccountRequest {
        @NotBlank(message = "카카오 액세스 토큰을 입력해주세요.")
        @Schema(description = "카카오 accessToken을 주세요.")
        private String token;

        @NotBlank(message = "탈퇴 이유를 입력해주세요.")
        @Schema(description = "탈퇴 이유를 입력해주세요.")
        private String reasonToLeave;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class TestLoginRequest {
        private String email;
        private String password;
        private String name;
        private String nickname;
        private String address;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @Schema(description = "로그인을 위한 응답 객체")
    public static class LoginResponse {
        private String accessToken;
        private String refreshToken;
        private Long memberId;
        private String process;

        public static LoginResponse from(TokenInfoResponse tokenInfoResponse, String process, Long memberId) {
            return LoginResponse.builder()
                    .accessToken(tokenInfoResponse.getAccessToken())
                    .refreshToken(tokenInfoResponse.getRefreshToken())
                    .process(process)
                    .memberId(memberId)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "추가 정보 입력을 위한 요청 객체")
    public static class AdditionInfoRequest {
        @NotBlank(message = "자체 jwt 액세스 토큰을 입력해주세요.")
        @Schema(description = "자체 액세스 토큰을 입력해주세요.")
        private String accessToken;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Schema(description = "비밀번호를 입력해주세요")
        private String password;

        @NotBlank(message = "닉네임을 입력해주세요.")
        @Schema(description = "닉네임을 입력해주세요")
        private String nickname;

        @NotBlank(message = "실명을 입력해주세요.")
        @Schema(description = "실명을 입력해주세요")
        private String name;

        @NotBlank(message = "주소를 입력해주세요.")
        @Schema(description = "주소를 입력해주세요")
        private String address;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "닉네임 중복 검사를 위한 응답 객체")
    public static class CheckNicknameResponse {
        private String result;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "마이페이지 수정을 위한 객체")
    public static class MyPageEditDto {
        private String nickname;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "마이페이지를 위한 객체")
    public static class MyPageInfoDto {
        private String nickname;
    }
}