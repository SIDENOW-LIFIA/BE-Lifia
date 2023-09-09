package com.sidenow.domain.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

public abstract class MemberRequest {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "유저 회원가입 요청 객체")
    public static class SignUpMemberRequest {
        @NotBlank(message = "이메일을 입력하세요.")
        @Email(message = "올바른 이메일주소를 입력하세요.")
        private String email;

        @NotBlank(message = "비밀번호를 입력하세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력하세요.")
        private String password;

        @NotBlank(message = "이름을 입력하세요.")
        private String name;

        @NotBlank(message = "닉네임을 입력하세요.")
        @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력하세요.")
        private String nickname;

        @NotBlank(message = "아파트를 선택하세요.")
        private String apartment;

//        @NotBlank(message = "신분증을 첨부하세요.")
//        private MultipartFile multipartFile;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "유저 로그인 요청 객체")
    public static class MemberLoginRequest{

        @NotBlank(message = "이메일을 입력하세요.")
        @Email(message = "올바른 이메일주소를 입력하세요.")
        private String email;

        @NotBlank(message = "비밀번호를 입력하세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력하세요.")
        private String password;
    }
}
