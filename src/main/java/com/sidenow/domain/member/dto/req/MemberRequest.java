package com.sidenow.domain.member.dto.req;

import com.sidenow.domain.member.constant.MemberConstant;
import com.sidenow.domain.member.constant.MemberConstant.Role;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class MemberRequest {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "유저 회원가입 요청 객체")
    public static class SignUpMemberRequest {
        @NotBlank(message = "이메일을 입력하세요.")
        @Email(message = "올바른 이메일주소를 입력하세요.")
        @Schema(description = "이메일 주소를 입력해주세요.", example = "jongseunghan@naver.com")
        private String email;

        @NotBlank(message = "비밀번호를 입력하세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력하세요.")
        @Schema(description = "비밀번호를 입력해주세요.", example = "hjs0324@")
        private String password;

        @NotBlank(message = "이름을 입력하세요.")
        @Schema(description = "이름을 입력해주세요.", example = "한종승")
        private String name;

        @NotBlank(message = "닉네임을 입력하세요.")
        @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력하세요.")
        @Schema(description = "닉네임을 입력해주세요.", example = "bellwin")
        private String nickname;

        @NotBlank(message = "아파트를 선택하세요.")
        @Schema(description = "거주 아파트를 선택해주세요.", example = "우성아파트")
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
        @Schema(description = "유저 이메일", example = "jongseunghan@naver.com")
        private String email;

        @NotBlank(message = "비밀번호를 입력하세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력하세요.")
        @Schema(description = "유저 비밀번호", example = "hjs0324@")
        private String password;

        public Member toMember(PasswordEncoder passwordEncoder) {
            return Member.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(Role.Role_USER)
                    .build();
        }
        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "토큰 요청 객체")
    public static class MemberTokenRequest{

        private String accessToken;
        private String refreshToken;
    }
}
