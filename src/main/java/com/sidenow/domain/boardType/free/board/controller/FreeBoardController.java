package com.sidenow.domain.boardType.free.board.controller;

import com.sidenow.domain.boardType.free.board.dto.req.FreeBoardRequest.FreeBoardRegisterPostRequest;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.FreeBoardCheck;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.FreeBoardGetPostResponse;
import com.sidenow.domain.boardType.free.board.service.FreeBoardService;
import com.sidenow.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.sidenow.domain.boardType.free.board.constant.FreeBoardConstants.FreeBoardResponseMessage.REGISTER_FREE_BOARD_POST_SUCCESS;
import static com.sidenow.domain.boardType.free.board.constant.FreeBoardConstants.FreeBoardResponseMessage.GET_FREE_BOARD_POST_SUCCESS;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/board/free")
@Tag(name = "FreeBoard API", description = "자유게시판 API 입니다.")
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    @PostMapping(value = "/post", consumes = {"multipart/form-data"})
    @Operation(summary = "자유게시판 글 등록", description = "로그인 필수 / 미로그인 시 접근 불가")
    public ResponseEntity<ResponseDto<FreeBoardCheck>> registerFreeBoardPost(@RequestPart(required = false)List<MultipartFile> multipartFile, @RequestBody FreeBoardRegisterPostRequest freeBoardRegisterPostRequest) {
        FreeBoardCheck registerFreeBoardPost = freeBoardService.registerFreeBoardPost(multipartFile, freeBoardRegisterPostRequest);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), REGISTER_FREE_BOARD_POST_SUCCESS.getMessage(), registerFreeBoardPost));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "자유게시판 게시글 단건 조회", description = "로그인 필수 / 미로그인 시 접근 불가")
    public ResponseEntity<ResponseDto<FreeBoardGetPostResponse>> getFreeBoardPost(@PathVariable Long freeBoardPostId) {
        FreeBoardGetPostResponse getFreeBoardPost = freeBoardService.getFreeBoardPost(freeBoardPostId);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_FREE_BOARD_POST_SUCCESS.getMessage(), getFreeBoardPost));
    }
}
