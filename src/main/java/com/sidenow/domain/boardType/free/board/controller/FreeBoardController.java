package com.sidenow.domain.boardType.free.board.controller;

import com.sidenow.domain.boardType.free.board.dto.req.FreeBoardRequest.CreateFreeBoardPostRequest;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.CreatePostResponse;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.ReadFreeBoardResponse;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.ReadFreeBoardPostDetailResponse;
import com.sidenow.domain.boardType.free.board.service.FreeBoardService;
import com.sidenow.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sidenow.domain.boardType.free.board.constant.FreeBoardConstants.FreeBoardResponseMessage.CREATE_FREE_BOARD_POST_SUCCESS;
import static com.sidenow.domain.boardType.free.board.constant.FreeBoardConstants.FreeBoardResponseMessage.READ_FREE_BOARD_POST_DETAIL_SUCCESS;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/board/free")
@Tag(name = "FreeBoard API", description = "자유게시판 API 입니다.")
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    @PostMapping("/post")
    @Operation(summary = "자유게시판 글 등록", description = "로그인 X의 경우 접근 불가")
    public ResponseEntity<ResponseDto<CreatePostResponse>> createPost(@RequestBody CreateFreeBoardPostRequest request) {
        freeBoardService.createPost(request);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_FREE_BOARD_POST_SUCCESS.getMessage()));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "자유게시판 게시글 상세 조회", description = "로그인 X의 경우 접근 불가")
    public ResponseEntity<ResponseDto<ReadFreeBoardPostDetailResponse>> readPostDetail(@PathVariable Long postId) {
        ReadFreeBoardPostDetailResponse postDetail = freeBoardService.readPostDetail(postId);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), READ_FREE_BOARD_POST_DETAIL_SUCCESS.getMessage(), postDetail));
    }

    @PostMapping
    @Operation(summary = "자유게시판 게시글 전체 조회", description = "로그인 X의 경우 접근 불가")
    public ResponseEntity<ResponseDto<ReadFreeBoardResponse>> readFreeBoards(@PathVariable Long postId) {

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), READ_FREE_BOARD_POST_DETAIL_SUCCESS.getMessage()));
    }

}
