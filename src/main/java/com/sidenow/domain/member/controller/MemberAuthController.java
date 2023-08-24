package com.sidenow.domain.member.controller;

import com.sidenow.domain.member.dto.MemberDto;
import com.sidenow.domain.member.dto.req.MemberRequest.SignUpMemberRequest;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberCheck;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.service.MemberAuthService;
import com.sidenow.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sidenow.domain.member.constant.MemberConstant.EMemberResponseMessage.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
@Tag(name = "Member Auth API", description = "회원 인증 API입니다.")
public class MemberAuthController {



}
