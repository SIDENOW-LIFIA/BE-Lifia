package com.sidenow.domain.member.controller;

import com.sidenow.domain.member.dto.req.MemberRequest.SignUpMemberRequest;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberCheck;
import com.sidenow.domain.member.exception.MemberEmailAuthCodeException;
import com.sidenow.domain.member.service.MemberSignUpService;
import com.sidenow.global.config.aws.service.AwsSesService;
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

import static com.sidenow.domain.member.constant.MemberConstant.MemberSuccessMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
@Tag(name = "회원 가입", description = "회원 가입 API입니다.")
public class MemberSignUpController {

    private final MemberSignUpService memberSignUpService;
    private final AwsSesService awsSesService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<ResponseDto<MemberCheck>> signUp(@RequestBody SignUpMemberRequest signUpMemberRequest) {
        log.info("Sign Up Api Start");
        MemberCheck memberCheck = memberSignUpService.signUp(signUpMemberRequest);
        log.info("Sign Up Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), MEMBER_SIGN_UP_SUCCESS.getMessage(), memberCheck));
    }

    @PostMapping("/email/duplicate")
    @Operation(summary = "이메일 중복체크")
    public ResponseEntity<ResponseDto<Boolean>> checkEmailDuplicate(String email) {
        log.info("Check Email Duplicate Api Start");
        boolean isEmailDuplicate = memberSignUpService.checkEmailDuplicate(email);
        log.info("Check Email Duplicate Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), MEMBER_CHECK_EMAIL_DUPLICATE_SUCCESS.getMessage(), isEmailDuplicate));
    }

    @PostMapping("/nickname/duplicate")
    @Operation(summary = "닉네임 중복체크")
    public ResponseEntity<ResponseDto<Boolean>> checkNicknameDuplicate(String nickname) {
        log.info("Check Nickname Duplicate Api Start");
        boolean isNicknameDuplicate = memberSignUpService.checkNicknameDuplicate(nickname);
        log.info("Check Nickname Duplicate Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), MEMBER_CHECK_NICKNAME_DUPLICATE_SUCCESS.getMessage(), isNicknameDuplicate));
    }

    @PostMapping("/mail")
    @Operation(summary = "이메일 인증코드 전송")
    public ResponseEntity<ResponseDto<String>> sendAuthMail(String email) {
        log.info("Send Auth Mail Api Start");
        try {
            String message = awsSesService.send(email);
            log.info("Send Auth Mail Api End");
            return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), MEMBER_SEND_EMAIL_AUTH_CODE_SUCCESS.getMessage(), message));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @PostMapping("/mail/auth")
    @Operation(summary = "이메일 인증코드 검증")
    public ResponseEntity<ResponseDto<Boolean>> checkAuthCode(String email, String authCode) {
        log.info("Verify Mail Code Api Start");
        boolean isAuthCodeVerified = awsSesService.verifyEmailCode(email, authCode);
        if (!isAuthCodeVerified) {
            throw new MemberEmailAuthCodeException();
        }
        log.info("Verify Mail Code Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), MEMBER_EMAIL_AUTH_CODE_VERIFIED.getMessage(), true));

    }
}
