package com.sidenow.domain.boardType.vote.board.controller;

import com.sidenow.domain.boardType.vote.board.dto.req.VoteBoardRequest.VoteBoardRegisterPostRequest;
import com.sidenow.domain.boardType.vote.board.dto.req.VoteBoardRequest.VoteBoardUpdatePostRequest;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteBoardResponse.AllVoteBoards;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteBoardResponse.VoteBoardCheck;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteBoardResponse.VoteBoardGetPostResponse;
import com.sidenow.domain.boardType.vote.board.entity.VoteBoard;
import com.sidenow.domain.boardType.vote.board.service.VoteBoardService;
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

import static com.sidenow.domain.boardType.vote.board.constant.VoteBoardConstants.VoteBoardResponseMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/voteBoard")
@Tag(name = "투표게시판 API", description = "VoteBoard")
public class VoteBoardController {

    private final VoteBoardService voteBoardService;

    @PostMapping(value = "/post", consumes = {"multipart/form-data"})
    @Operation(summary = "투표게시판 글 등록", description = "로그인 필수 / 미로그인 시 접근 불가")
    public ResponseEntity<ResponseDto<VoteBoardCheck>> registerVoteBoardPost(@RequestPart(required = false)List<MultipartFile> multipartFile, @RequestBody VoteBoardRegisterPostRequest voteBoardRegisterPostRequest) {
        log.info("Register VoteBoard Post Controller 진입");
        VoteBoardCheck result = voteBoardService.registerVoteBoardPost(multipartFile, voteBoardRegisterPostRequest);
        log.info("Register VoteBoard Post Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), REGISTER_VOTE_BOARD_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/post/{postId}")
    @Operation(summary = "투표게시판 게시글 단건 조회", description = "로그인 필수 / 미로그인 시 접근 불가")
    public ResponseEntity<ResponseDto<VoteBoardGetPostResponse>> getVoteBoardPost(@PathVariable Long postId) {
        log.info("Get VoteBoard Post Controller 진입");
        VoteBoardGetPostResponse result = voteBoardService.getVoteBoardPost(postId);
        log.info("Get VoteBoard Post Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_VOTE_BOARD_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/{page}")
    @Operation(summary = "투표게시판 게시글 전체 조회", description = "로그인 필수 / 미로그인 시 접근 불가")
    public ResponseEntity<ResponseDto<AllVoteBoards>> getAllVoteBoard(@PathVariable @Nullable Integer page) {
        log.info("Get All VoteBoard Controller 진입");
        AllVoteBoards result = voteBoardService.getVoteBoardPostList(page);
        log.info("Get All VoteBoard Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_VOTE_BOARD_POST_LIST_SUCCESS.getMessage(), result));
    }

    @PostMapping(value = "/post/{postId}", consumes = {"multipart/form-data"})
    @Operation(summary = "투표게시판 글 수정", description = "로그인 필수 / 미로그인 시 접근 불가")
    public ResponseEntity<ResponseDto<VoteBoardCheck>> updateVoteBoardPost(@PathVariable Long postId, @RequestPart(required = false)List<MultipartFile> multipartFile, @RequestBody VoteBoardUpdatePostRequest voteBoardUpdatePostRequest) {
        log.info("Update VoteBoard Post Controller 진입");
        VoteBoardCheck result = voteBoardService.updateVoteBoardPost(multipartFile, postId, voteBoardUpdatePostRequest);
        log.info("Update VoteBoard Post Controller 종료");

        return ResponseEntity.ok(ResponseDto.update(HttpStatus.OK.value(), UPDATE_VOTE_BOARD_POST_SUCCESS.getMessage(), result));
    }

    @DeleteMapping(value = "/post/{postId}")
    @Operation(summary = "투표게시판 글 삭제")
    public ResponseEntity<ResponseDto<VoteBoardCheck>> deleteVoteBoardPost(@PathVariable Long postId) {
        log.info("Delete VoteBoard Post Controller 진입");
        VoteBoardCheck result = voteBoardService.deleteVoteBoardPost(postId);
        log.info("Delete VoteBoard Post Controller 종료");

        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.OK.value(), DELETE_VOTE_BOARD_POST_SUCCESS.getMessage(), result));
    }

}
