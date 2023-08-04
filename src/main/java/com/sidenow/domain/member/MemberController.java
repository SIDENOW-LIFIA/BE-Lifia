package com.sidenow.domain.member;

import com.sidenow.global.ResponseDto;
import com.sidenow.domain.member.dto.MemberJoinRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api//v1/members")
@Validated
@Tag(name = "Member", description = "유저 API")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(summary = "회원가입", description = "회원가입을 하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "회원가입 실패")
    })
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody @Valid MemberJoinRequestDto dto, BindingResult bindingResult) throws Exception{
        logger.info("SignUp Api Start");
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(new ResponseDto("Field Error"));
        }

        Member member = Member.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .nickname(dto.getNickname())
                .address(dto.getAddress())
                .build();

        memberService.join(member);
        logger.info("SignUp Api End");
        return ResponseEntity.ok().body(new ResponseDto("Sign Up Success"));
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "회원삭제 성공"),
            @ApiResponse(responseCode = "400", description = "회원삭제 실패")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Delete Member Api Start");
        memberService.delete(id);
        logger.info("Delete Member Api End");
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "이메일 중복 검증", description = "이메일 중복 검증 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용 가능한 이메일"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일")
    })
    @GetMapping("/{email}/exists")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email) {
        return ResponseEntity.ok(memberService.validateDuplicateEmail(email));
    }

    @Operation(summary = "닉네임 중복 검증", description = "닉네임 중복 검증 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용 가능한 닉네임"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 닉네임")
    })
    @GetMapping("/{nickname}/exists")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@PathVariable String nickname) {
        return ResponseEntity.ok(memberService.validateDuplicateNickname(nickname));
    }

}
