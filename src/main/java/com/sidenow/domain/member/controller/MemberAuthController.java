package com.sidenow.domain.member.controller;

import com.sidenow.domain.member.dto.req.MemberRequest;
import com.sidenow.domain.member.dto.req.MemberRequest.MemberLoginRequest;
import com.sidenow.domain.member.dto.res.MemberResponse;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberLoginResponse;
import com.sidenow.global.dto.ResponseDto;
import com.sidenow.global.dto.TokenInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
@Tag(name = "Member Auth API", description = "회원 인증 API입니다.")
public class MemberAuthController {

    @PostMapping("/login")
    @Operation(summary = "유저 로그인")
    public ResponseEntity<ResponseDto<MemberResponse.MemberLoginResponse>> login(@RequestBody MemberRequest.MemberLoginRequest memberLoginRequest){

    }




}
