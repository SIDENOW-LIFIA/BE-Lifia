package com.sidenow.domain.boardType.childCare.board.controller;

import com.sidenow.domain.boardType.childCare.board.dto.req.ChildCareBoardRequest.ChildCareBoardRegisterPostRequest;
import com.sidenow.domain.boardType.childCare.board.dto.req.ChildCareBoardRequest.ChildCareBoardUpdatePostRequest;
import com.sidenow.domain.boardType.childCare.board.dto.res.ChildCareBoardResponse.AllChildCareBoards;
import com.sidenow.domain.boardType.childCare.board.dto.res.ChildCareBoardResponse.ChildCareBoardCheck;
import com.sidenow.domain.boardType.childCare.board.dto.res.ChildCareBoardResponse.ChildCareBoardGetPostResponse;
import com.sidenow.domain.boardType.childCare.board.entity.ChildCareBoard;
import com.sidenow.domain.boardType.childCare.board.service.ChildCareBoardService;
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

import static com.sidenow.domain.boardType.childCare.board.constant.ChildCareBoardConstants.ChildCareBoardResponseMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/childCareBoard")
@Tag(name = "육아게시판 API", description = "ChildCareBoard")
public class ChildCareBoardController {

    private final ChildCareBoardService childCareBoardService;

    @PostMapping(value = "/post", consumes = {"multipart/form-data"})
    @Operation(summary = "육아게시판 글 등록", description = "로그인 필수 / 미로그인 시 접근 불가")
    public ResponseEntity<ResponseDto<ChildCareBoardCheck>> registerChildCareBoardPost(@RequestPart(required = false)List<MultipartFile> multipartFile, @RequestBody ChildCareBoardRegisterPostRequest childCareBoardRegisterPostRequest) {
        log.info("Register ChildCareBoard Post Controller 진입");
        ChildCareBoardCheck result = ChildCareBoardService.registerChildCareBoardPost(multipartFile, childCareBoardRegisterPostRequest);
        log.info("Register ChildCareBoard Post Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), REGISTER_CHILDCARE_BOARD_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/post/{postId}")
    @Operation(summary = "육아게시판 게시글 단건 조회", description = "로그인 필수 / 미로그인 시 접근 불가")
    public ResponseEntity<ResponseDto<ChildCareBoardGetPostResponse>> getChildCareBoardPost(@PathVariable Long postId) {
        log.info("Get ChildCareBoard Post Controller 진입");
        ChildCareBoardGetPostResponse result = childCareBoardService.getChildCareBoardPost(postId);
        log.info("Get ChildCareBoard Post Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_CHILDCARE_BOARD_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/{page}")
    @Operation(summary = "육아게시판 게시글 전체 조회", description = "로그인 필수 / 미로그인 시 접근 불가")
    public ResponseEntity<ResponseDto<AllChildCareBoards>> getAllChildCareBoard(@PathVariable @Nullable Integer page) {
        log.info("Get All ChildCareBoard Controller 진입");
        AllChildCareBoards result = childCareBoardService.getChildCareBoardPostList(page);
        log.info("Get All ChildCareBoard Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_CHILDCARE_BOARD_POST_LIST_SUCCESS.getMessage(), result));
    }

    @PostMapping(value = "/post/{postId}", consumes = {"multipart/form-data"})
    @Operation(summary = "육아게시판 글 수정", description = "로그인 필수 / 미로그인 시 접근 불가")
    public ResponseEntity<ResponseDto<ChildCareBoardCheck>> updateChildCareBoardPost(@PathVariable Long postId, @RequestPart(required = false)List<MultipartFile> multipartFile, @RequestBody ChildCareBoardUpdatePostRequest childCareBoardUpdatePostRequest) {
        log.info("Update ChildCareBoard Post Controller 진입");
        ChildCareBoardCheck result = childCareBoardService.updateChildCareBoardPost(multipartFile, postId, childCareBoardUpdatePostRequest);
        log.info("Update ChildCareBoard Post Controller 종료");

        return ResponseEntity.ok(ResponseDto.update(HttpStatus.OK.value(), UPDATE_CHILDCARE_BOARD_POST_SUCCESS.getMessage(), result));
    }

    @DeleteMapping(value = "/post/{postId}")
    @Operation(summary = "육아게시판 글 삭제")
    public ResponseEntity<ResponseDto<ChildCareBoardCheck>> deleteChildCareBoardPost(@PathVariable Long postId) {
        log.info("Delete ChildCareBoard Post Controller 진입");
        ChildCareBoardCheck result = childCareBoardService.deleteChildCareBoardPost(postId);
        log.info("Delete ChildCareBoard Post Controller 종료");

        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.OK.value(), DELETE_CHILDCARE_BOARD_POST_SUCCESS.getMessage(), result));
    }

}
