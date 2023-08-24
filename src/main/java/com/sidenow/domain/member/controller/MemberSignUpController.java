package com.sidenow.domain.member.controller;

import com.sidenow.domain.member.dto.req.MemberRequest;
import com.sidenow.domain.member.dto.req.MemberRequest.SignUpMemberRequest;
import com.sidenow.domain.member.dto.res.MemberResponse;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberCheck;
import com.sidenow.domain.member.service.MemberAuthService;
import com.sidenow.domain.member.service.MemberSignUpService;
import com.sidenow.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sidenow.domain.member.constant.MemberConstant.EMemberResponseMessage.SIGN_UP_SUCCESS;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
@Tag(name = "회원 가입", description = "회원 가입을 완료합니다.")
public class MemberSignUpController {

    private final MemberSignUpService memberSignUpService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<ResponseDto<MemberCheck>> signup(@RequestBody SignUpMemberRequest signUpMemberRequest) {
        log.info("Sign Up Api Start");
        MemberCheck memberCheck = memberSignUpService.signUp(signUpMemberRequest);
        log.info("Sign Up Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), SIGN_UP_SUCCESS.getMessage(), memberCheck));
    }
}
