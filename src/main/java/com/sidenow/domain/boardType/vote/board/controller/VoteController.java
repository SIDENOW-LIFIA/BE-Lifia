package com.sidenow.domain.boardType.vote.board.controller;

import com.sidenow.domain.boardType.vote.board.dto.req.VoteRequest.VoteCreateRequest;
import com.sidenow.domain.boardType.vote.board.dto.req.VoteRequest.VoteUpdateRequest;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteResponse.AllVotes;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteResponse.VoteCreateResponse;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteResponse.VoteGetResponse;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteResponse.VoteUpdateResponse;
import com.sidenow.domain.boardType.vote.board.service.VoteService;
import com.sidenow.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.sidenow.domain.boardType.vote.board.constant.VoteConstants.VoteResponseMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/vote")
@Tag(name = "투표해요 API", description = "Vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping(value = "/board", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "투표해요 게시글 등록", description = "게시글을 등록합니다.")
    public ResponseEntity<ResponseDto<VoteCreateResponse>> createVote(@RequestPart(required = false) MultipartFile image,
                                                                                @RequestPart VoteCreateRequest req) {
        log.info("Register Vote Controller 진입");
        VoteCreateResponse result = voteService.createVote(req, image);
        log.info("Register Vote Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_VOTE_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/board/{id}")
    @Operation(summary = "투표해요 게시글 단건 조회", description = "게시글을 단건 조회합니다.")
    public ResponseEntity<ResponseDto<VoteGetResponse>> getVote(@PathVariable Long id) {
        log.info("Get Vote Controller 진입");
        VoteGetResponse result = voteService.getVote(id);
        log.info("Get Vote Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_VOTE_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/{page}")
    @Operation(summary = "투표해요 게시글 목록 조회", description = "게시글 목록을 조회합니다.")
    public ResponseEntity<ResponseDto<AllVotes>> getAllVote(@PathVariable @Nullable Integer page) {
        log.info("Get All Vote Controller 진입");
        AllVotes result = voteService.getVoteList(page);
        log.info("Get All Vote Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_VOTE_POST_LIST_SUCCESS.getMessage(), result));
    }

    @PutMapping(value = "/board/{id}", consumes = {"multipart/form-data"})
    @Operation(summary = "투표해요 게시글 수정", description = "게시글을 수정합니다.")
    public ResponseEntity<ResponseDto<VoteUpdateResponse>> updateVote(@PathVariable Long id, @RequestPart VoteUpdateRequest req, @RequestPart(required = false) MultipartFile image) {
        log.info("Update Vote Controller 진입");
        VoteUpdateResponse result = voteService.updateVote(id, req, image);
        log.info("Update Vote Controller 종료");

        return ResponseEntity.ok(ResponseDto.update(HttpStatus.OK.value(), UPDATE_VOTE_POST_SUCCESS.getMessage(), result));
    }

    @DeleteMapping(value = "/board/{id}")
    @Operation(summary = "투표해요 게시글 삭제", description = "게시글을 삭제합니다.")
    public ResponseEntity<ResponseDto> deleteVote(@PathVariable Long id) {
        log.info("Delete Vote Controller 진입");
        voteService.deleteVote(id);
        log.info("Delete Vote Controller 종료");

        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.OK.value(), DELETE_VOTE_POST_SUCCESS.getMessage()));
    }

    @PostMapping("/board/{id}")
    @Operation(summary = "투표해요 게시글 좋아요", description = "사용자가 게시글 좋아요를 누릅니다.")
    public ResponseEntity<ResponseDto<String>> likeVote(@PathVariable Long id) {
        log.info("Like Vote Controller 진입");
        String result = voteService.updateLikeOfVote(id);
        log.info("Like Vote Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LIKE_VOTE_SUCCESS.getMessage(), result));
    }

}
