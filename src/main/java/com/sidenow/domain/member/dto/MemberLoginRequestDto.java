package com.sidenow.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
@Schema(description = "로그인 화면에서 유저로부터 전달받는 데이터입니다.")
public class MemberLoginRequestDto {
    @NotBlank(message = "이메일을 입력하세요.")
    @Email
    private String email;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
}
