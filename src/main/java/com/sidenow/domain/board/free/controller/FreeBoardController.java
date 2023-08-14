package com.sidenow.domain.board.free.controller;

import com.sidenow.domain.board.free.dto.FreeBoardDto;
import com.sidenow.domain.board.free.service.FreeBoardService;
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

import static com.sidenow.domain.board.constant.BoardConstant.EBoardResponseMessage.CREATE_POST_SUCCESS;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/board/free")
@Tag(name = "FreeBoard API", description = "자유게시판 API 입니다.")
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    @PostMapping("/post")
    @Operation(summary = "자유게시판 글 등록", description = "로그인 X의 경우 접근 불가")
    public ResponseEntity<ResponseDto> save(@RequestBody FreeBoardDto.CreateFreeBoardRequest request) {
        freeBoardService.save(request);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_POST_SUCCESS.getMessage()));
    }

}
