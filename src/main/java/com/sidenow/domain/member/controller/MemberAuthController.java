package com.sidenow.domain.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
@Tag(name = "Member Auth API", description = "회원 인증 API입니다.")
public class MemberAuthController {



}
