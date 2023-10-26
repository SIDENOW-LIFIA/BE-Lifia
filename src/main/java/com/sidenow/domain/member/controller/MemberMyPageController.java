package com.sidenow.domain.member.controller;

import com.amazonaws.Response;
import com.sidenow.domain.freeboard.board.dto.res.FreeBoardResponse;
import com.sidenow.domain.freeboard.board.dto.res.FreeBoardResponse.FreeBoardGetResponse;
import com.sidenow.domain.member.dto.res.MemberResponse;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberInfoResponse;
import com.sidenow.domain.member.service.MemberMyPageService;
import com.sidenow.global.dto.ResponseDto;
import io.lettuce.core.GeoArgs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sidenow.domain.member.constant.MemberConstant.MemberSuccessMessage.MEMBER_INFO_GET_SUCCESS;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
@Tag(name = "마이페이지 API", description = "My Page")
public class MemberMyPageController {
    private final MemberMyPageService memberMyPageService;

    @GetMapping("/info")
    @Operation(summary = "회원 정보 조회", description = "이메일, 이름, 닉네임, 아파트")
    public ResponseEntity<ResponseDto<MemberInfoResponse>> getMemberInfo(){
        log.info("Get Member Info API 진입");
        MemberInfoResponse result = memberMyPageService.getMemberInfo();
        log.info("Get Member Info API 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), MEMBER_INFO_GET_SUCCESS.getMessage(), result));
    }
}
