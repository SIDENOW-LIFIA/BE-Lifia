package com.sidenow.domain.boardType.free.board.controller;

import com.sidenow.domain.boardType.free.board.dto.req.FreeBoardRequest.FreeBoardRegisterPostRequest;
import com.sidenow.domain.boardType.free.board.dto.req.FreeBoardRequest.FreeBoardUpdatePostRequest;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.AllFreeBoards;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.FreeBoardCheck;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.FreeBoardGetPostResponse;
import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.board.service.FreeBoardService;
import com.sidenow.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.sidenow.domain.boardType.free.board.constant.FreeBoardConstants.FreeBoardResponseMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/freeBoard")
@Tag(name = "자유게시판 API", description = "FreeBoard")
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    @PostMapping(value = "/post", consumes = {"multipart/form-data"})
    @Operation(summary = "자유게시판 글 등록", description = "로그인 필수 / 미로그인 시 접근 불가")
    public ResponseEntity<ResponseDto<FreeBoardCheck>> registerFreeBoardPost(@RequestPart(required = false)List<MultipartFile> multipartFile, @RequestBody FreeBoardRegisterPostRequest freeBoardRegisterPostRequest) {
        log.info("Register FreeBoard Post Controller 진입");
        FreeBoardCheck result = freeBoardService.registerFreeBoardPost(multipartFile, freeBoardRegisterPostRequest);
        log.info("Register FreeBoard Post Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), REGISTER_FREE_BOARD_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/post/{postId}")
    @Operation(summary = "자유게시판 게시글 단건 조회", description = "로그인 필수 / 미로그인 시 접근 불가")
    public ResponseEntity<ResponseDto<FreeBoardGetPostResponse>> getFreeBoardPost(@PathVariable Long postId) {
        log.info("Get FreeBoard Post Controller 진입");
        FreeBoardGetPostResponse result = freeBoardService.getFreeBoardPost(postId);
        log.info("Get FreeBoard Post Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_FREE_BOARD_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/{page}")
    @Operation(summary = "자유게시판 게시글 전체 조회", description = "로그인 필수 / 미로그인 시 접근 불가")
    public ResponseEntity<ResponseDto<AllFreeBoards>> getAllFreeBoard(@PathVariable @Nullable Integer page) {
        log.info("Get All FreeBoard Controller 진입");
        AllFreeBoards result = freeBoardService.getFreeBoardPostList(page);
        log.info("Get All FreeBoard Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_FREE_BOARD_POST_LIST_SUCCESS.getMessage(), result));
    }

    @PostMapping(value = "/post/{postId}", consumes = {"multipart/form-data"})
    @Operation(summary = "자유게시판 글 수정", description = "로그인 필수 / 미로그인 시 접근 불가")
    public ResponseEntity<ResponseDto<FreeBoardCheck>> updateFreeBoardPost(@PathVariable Long postId, @RequestPart(required = false)List<MultipartFile> multipartFile, @RequestBody FreeBoardUpdatePostRequest freeBoardUpdatePostRequest) {
        log.info("Update FreeBoard Post Controller 진입");
        FreeBoardCheck result = freeBoardService.updateFreeBoardPost(multipartFile, postId, freeBoardUpdatePostRequest);
        log.info("Update FreeBoard Post Controller 종료");

        return ResponseEntity.ok(ResponseDto.update(HttpStatus.OK.value(), UPDATE_FREE_BOARD_POST_SUCCESS.getMessage(), result));
    }

    @DeleteMapping(value = "/post/{postId}")
    @Operation(summary = "자유게시판 글 삭제")
    public ResponseEntity<ResponseDto<FreeBoardCheck>> deleteFreeBoardPost(@PathVariable Long postId) {
        log.info("Delete FreeBoard Post Controller 진입");
        FreeBoardCheck result = freeBoardService.deleteFreeBoardPost(postId);
        log.info("Delete FreeBoard Post Controller 종료");

        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.OK.value(), DELETE_FREE_BOARD_POST_SUCCESS.getMessage(), result));
    }

}
