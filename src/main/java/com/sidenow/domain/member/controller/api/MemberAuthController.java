package com.sidenow.domain.member.controller.api;

import com.sidenow.domain.member.dto.MemberDto;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.service.auth.MemberAuthenticationService;
import com.sidenow.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sidenow.domain.member.constant.MemberConstant.EMemberResponseMessage.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/members")
@Tag(name = "Member Auth API", description = "회원 인증 API입니다.")
public class MemberAuthController {

    private final MemberAuthenticationService authenticationService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(summary = "회원가입", description = "일반 회원가입을 합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<Member>> signup(@RequestBody MemberDto.SignUpRequest signUpRequest) {
        logger.info("회원가입 API START");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), SIGN_UP_SUCCESS.getMessage(), this.authenticationService.signUp(signUpRequest)));
    }


    @Operation(summary = "카카오 로그인", description = "카카오 로그인을 합니다.")
    @PostMapping("/auth/kakao")
    public ResponseEntity<ResponseDto<MemberDto.LoginResponse>> login(@Valid @RequestBody MemberDto.LoginRequest loginRequest) {
        logger.info("카카오 로그인 API START");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LOGIN_SUCCESS.getMessage(), this.authenticationService.kakaoLogin(loginRequest)));
    }

    @Operation(summary = "추가 정보 입력", description = "추가 정보를 입력합니다.")
    @PostMapping("/additional-info")
    public ResponseEntity<ResponseDto<MemberDto.LoginResponse>> additionalInfo(@Valid @RequestBody MemberDto.AdditionInfoRequest additionInfoRequest) {
        logger.info("추가 정보 입력 API START");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), SIGN_UP_SUCCESS.getMessage(), this.authenticationService.socialLoginSignUp(additionInfoRequest)));
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 합니다.")
    @DeleteMapping
    public ResponseEntity<ResponseDto> delete(@Valid @RequestBody MemberDto.DeleteAccountRequest deleteAccountRequest) {
        logger.info("회원 탈퇴 API START");
        this.authenticationService.deleteAccount(deleteAccountRequest);
        logger.info("회원 탈퇴 API END");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), DELETE_SUCCESS.getMessage()));
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 합니다.")
    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout(@Valid @RequestBody MemberDto.LoginRequest loginRequest) {
        log.info("로그아웃 API START");
        this.authenticationService.kakaoLogout(loginRequest);
        log.info("로그아웃 API END");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LOGOUT_SUCCESS.getMessage()));
    }

    // 테스트를 위한 API
    @PostMapping("/auth/test")
    public ResponseEntity<ResponseDto<MemberDto.LoginResponse>> login(@Valid @RequestBody MemberDto.TestLoginRequest testLoginRequest) {
        log.info("테스트 로그인 API START");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LOGIN_SUCCESS.getMessage(), this.authenticationService.testLogin(testLoginRequest)));
    }

}
