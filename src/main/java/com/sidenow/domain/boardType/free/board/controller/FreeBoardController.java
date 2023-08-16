package com.sidenow.domain.boardType.free.board.controller;

import com.sidenow.domain.boardType.free.board.dto.FreeBoardDto;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse;
import com.sidenow.domain.boardType.free.board.service.FreeBoardService;
import com.sidenow.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sidenow.domain.boardType.constant.BoardConstant.EBoardResponseMessage.CREATE_POST_SUCCESS;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/board/free")
@Tag(name = "FreeBoard API", description = "자유게시판 API 입니다.")
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    @PostMapping("/post")
    @Operation(summary = "자유게시판 글 등록", description = "로그인 X의 경우 접근 불가")
    public ResponseEntity<ResponseDto<FreeBoardResponse.CreatePostResponse>> createPost(@RequestBody FreeBoardDto.CreateFreeBoardRequest request) {
        freeBoardService.createPost(request);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_POST_SUCCESS.getMessage()));
    }

    @PostMapping("/{postId}")
    @Operation(summary = "자유게시판 게시글 상세 조회", description = "로그인 X의 경우 접근 불가")
    public ResponseEntity<ResponseDto<FreeBoardResponse.ReadPostDetailResponse>> readPostDetail(@PathVariable Long postId) {
        freeBoardService.
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_POST_SUCCESS.getMessage()));
    }

}
