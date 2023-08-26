package com.sidenow.domain.member.controller;

import com.sidenow.domain.member.constant.MemberConstant.MemberSuccessMessage;
import com.sidenow.domain.member.dto.req.MemberRequest.MemberLoginRequest;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberLoginResponse;
import com.sidenow.domain.member.service.MemberAuthService;
import com.sidenow.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
@Tag(name = "회원 인증 API", description = "회원 인증 API입니다.")
public class MemberAuthController {

    private final MemberAuthService memberAuthService;

    @PostMapping("/login")
    @Operation(summary = "유저 로그인")
    public ResponseEntity<ResponseDto<MemberLoginResponse>> login(@RequestBody MemberLoginRequest memberLoginRequest){
        log.info("Member Login Api Start");
        MemberLoginResponse memberLoginResponse = memberAuthService.login(memberLoginRequest);
        log.info("Member Login Api End");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), MemberSuccessMessage.MEMBER_LOGIN_SUCCESS.getMessage(), memberLoginResponse));
    }

    @PostMapping("/logout")
    @Operation(summary = "유저 로그아웃", description = "Access Token 또는 Refresh Token 필요")
    public ResponseEntity<ResponseDto> logout(@RequestHeader("Authorization") String authorization){
        log.info("Member Logout Api Start");
        memberAuthService.logout(authorization);
        log.info("Member Logout Api End");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), MemberSuccessMessage.MEMBER_LOGOUT_SUCCESS.getMessage()));
    }
}
