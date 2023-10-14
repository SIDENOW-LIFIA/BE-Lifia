package com.sidenow.domain.boardType.free.board.controller;

import com.sidenow.domain.boardType.free.board.dto.req.FreeBoardRequest.FreeBoardCreateRequest;
import com.sidenow.domain.boardType.free.board.dto.req.FreeBoardRequest.FreeBoardUpdateRequest;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.*;
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
@RequestMapping("/freeBoards")
@Tag(name = "자유게시판 API", description = "FreeBoard")
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    @PostMapping(value = "/board") // consumes = {"multipart/form-data"})
    @Operation(summary = "자유게시판 게시글 등록", description = "게시글을 등록합니다.")
    public ResponseEntity<ResponseDto<FreeBoardCreateResponse>> createFreeBoard(@RequestPart FreeBoardCreateRequest req, @RequestPart(required = false) MultipartFile image) {
        log.info("Register FreeBoard Controller 진입");
        FreeBoardCreateResponse result = freeBoardService.createFreeBoard(req, image);
        log.info("Register FreeBoard Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), REGISTER_FREE_BOARD_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/board/{id}")
    @Operation(summary = "자유게시판 게시글 단건 조회", description = "게시글을 단건 조회합니다.")
    public ResponseEntity<ResponseDto<FreeBoardGetResponse>> getFreeBoard(@PathVariable Long id) {
        log.info("Get FreeBoard Controller 진입");
        FreeBoardGetResponse result = freeBoardService.getFreeBoard(id);
        log.info("Get FreeBoard Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_FREE_BOARD_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/{page}")
    @Operation(summary = "자유게시판 게시글 목록 조회", description = "게시글 목록을 조회합니다.")
    public ResponseEntity<ResponseDto<AllFreeBoards>> getAllFreeBoard(@PathVariable @Nullable Integer page) {
        log.info("Get All FreeBoard Controller 진입");
        AllFreeBoards result = freeBoardService.getFreeBoardList(page);
        log.info("Get All FreeBoard Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_FREE_BOARD_POST_LIST_SUCCESS.getMessage(), result));
    }

    @PostMapping(value = "/board/{id}", consumes = {"multipart/form-data"})
    @Operation(summary = "자유게시판 게시글 수정", description = "게시글을 수정합니다.")
    public ResponseEntity<ResponseDto<FreeBoardUpdateResponse>> updateFreeBoard(@PathVariable Long id, @RequestPart FreeBoardUpdateRequest req, @RequestPart(required = false) MultipartFile image) {
        log.info("Update FreeBoard Controller 진입");
        FreeBoardUpdateResponse result = freeBoardService.updateFreeBoard(id, req, image);
        log.info("Update FreeBoard Controller 종료");

        return ResponseEntity.ok(ResponseDto.update(HttpStatus.OK.value(), UPDATE_FREE_BOARD_POST_SUCCESS.getMessage(), result));
    }

    @DeleteMapping(value = "/board/{id}")
    @Operation(summary = "자유게시판 게시글 삭제", description = "게시글을 삭제합니다.")
    public ResponseEntity<ResponseDto> deleteFreeBoard(@PathVariable Long id) {
        log.info("Delete FreeBoard Controller 진입");
        freeBoardService.deleteFreeBoard(id);
        log.info("Delete FreeBoard Controller 종료");

        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.OK.value(), DELETE_FREE_BOARD_POST_SUCCESS.getMessage()));
    }

    @PostMapping("/board/{id}")
    @Operation(summary = "자유게시판 게시글 좋아요", description = "사용자가 게시글 좋아요를 누릅니다.")
    public ResponseEntity<ResponseDto<String>> likeFreeBoard(@PathVariable Long id) {
        log.info("Like FreeBoard Controller 진입");
        String result = freeBoardService.updateLikeOfFreeBoard(id);
        log.info("Like FreeBoard Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LIKE_FREE_BOARD_SUCCESS.getMessage(), result));
    }

}
