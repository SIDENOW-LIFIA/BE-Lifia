package com.sidenow.domain.member.controller;

import com.sidenow.domain.member.constant.MemberConstant.MemberSuccessMessage;
import com.sidenow.domain.member.dto.req.MemberRequest.MemberLoginRequest;
import com.sidenow.domain.member.dto.req.MemberRequest.MemberTokenRequest;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberLoginResponse;
import com.sidenow.domain.member.service.MemberAuthService;
import com.sidenow.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sidenow.domain.member.constant.MemberConstant.MemberSuccessMessage.MEMBER_TOKEN_REFRESH_SUCCESS;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
@Tag(name = "회원 인증 API", description = "Login, Logout, Refresh Token")
public class MemberAuthController {

    private final MemberAuthService memberAuthService;

    @PostMapping("/login")
    @Operation(summary = "자체 로그인")
    public ResponseEntity<ResponseDto<MemberLoginResponse>> login(@Valid @RequestBody MemberLoginRequest memberLoginRequest){

        log.info("Member Login Api Start");
        MemberLoginResponse memberLoginResponse = memberAuthService.login(memberLoginRequest);
        log.info("Member Login Api End");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), MemberSuccessMessage.MEMBER_LOGIN_SUCCESS.getMessage(), memberLoginResponse));
    }

    @PostMapping("/re-issue")
    @Operation(summary = "토큰 재발급", description = "Refresh Token을 보내주세요.")
    public ResponseEntity<ResponseDto<MemberLoginResponse>> reIssueToken(@RequestBody MemberTokenRequest req) {

        log.info("Member Reissue Token Api Start");
        MemberLoginResponse memberLoginResponse = memberAuthService.reIssueToken(req);
        log.info("Member Reissue Token Api End");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), MEMBER_TOKEN_REFRESH_SUCCESS.getMessage(), memberLoginResponse));
    }
}
