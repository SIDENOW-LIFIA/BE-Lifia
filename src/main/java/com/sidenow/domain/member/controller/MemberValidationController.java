package com.sidenow.domain.member.controller;

import static com.sidenow.domain.member.constant.MemberConstant.EMemberResponseMessage.CHECK_ADDITIONALINFO_SUCCESS;
import static com.sidenow.domain.member.constant.MemberConstant.EMemberResponseMessage.CHECK_NICKNAME;

import com.sidenow.domain.member.dto.MemberDto;
import com.sidenow.domain.member.service.MemberMainService;
import com.sidenow.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/members")
@Tag(name = "Member Validation API")
public class MemberValidationController {

    private final MemberMainService validationService;

    @Operation(summary = "추가 정보 입력 검사", description = "추가 정보 입력 여부를 검사합니다.")
    @GetMapping("/additional-info")
    public ResponseEntity<ResponseDto> checkAdditionalInfo() {
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), CHECK_ADDITIONALINFO_SUCCESS.getMessage()));
    }

    @Operation(summary = "닉네임 중복 검사", description = "닉네임 중복 검사를 합니다.")
    @GetMapping("/nickname/{nickname}/available")
    public ResponseEntity<ResponseDto<MemberDto.CheckNicknameResponse>> checkNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), CHECK_NICKNAME.getMessage(), this.validationService.checkNickname(nickname)));
    }
}
