package com.sidenow.domain.boardType.vote.comment.controller;

import com.sidenow.domain.boardType.vote.comment.dto.req.VoteCommentRequest.VoteCommentCreateRequest;
import com.sidenow.domain.boardType.vote.comment.dto.req.VoteCommentRequest.VoteCommentUpdateRequest;
import com.sidenow.domain.boardType.vote.comment.dto.res.VoteCommentResponse.VoteGetCommentListResponse;
import com.sidenow.domain.boardType.vote.comment.service.VoteCommentService;
import com.sidenow.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sidenow.domain.boardType.vote.comment.constant.VoteCommentConstants.VoteCommentSuccessMessage.*;
import static com.sidenow.domain.boardType.vote.comment.dto.res.VoteCommentResponse.VoteCommentCreateResponse;
import static com.sidenow.domain.boardType.vote.comment.dto.res.VoteCommentResponse.VoteCommentUpdateResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board/vote")
@Tag(name = "투표해요 댓글 API", description = "VoteComment")
public class VoteCommentController {

    private final VoteCommentService voteCommentService;

    @PostMapping("/{postId}/comments")
    @Operation(summary = "투표해요 게시글의 댓글 작성")
    public ResponseEntity<ResponseDto<VoteCommentCreateResponse>> createVoteComment(@PathVariable("postId") Long voteId,
                                                                                     @RequestBody @Valid VoteCommentCreateRequest req) {
        log.info("Create Vote Comment Api Start");
        VoteCommentCreateResponse result = voteCommentService.createVoteComment(voteId, req);
        log.info("Create Vote Comment Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_VOTE_COMMENT_SUCCESS.getMessage(), result));
    }

    @GetMapping("/{postId}/comments")
    @Operation(summary = "투표해요 게시글의 댓글 전체 조회")
    public ResponseEntity<ResponseDto<List<VoteGetCommentListResponse>>> readVoteComments(@PathVariable("postId") Long voteId) {
        log.info("Read Vote Comments Api Start");
        List<VoteGetCommentListResponse> readVoteComments = voteCommentService.getVoteCommentList(voteId);
        log.info("Read Vote Comments Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), READ_VOTE_COMMENT_SUCCESS.getMessage(), readVoteComments));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "투표해요 게시글의 댓글 삭제")
    public ResponseEntity<ResponseDto> deleteVoteComment(@PathVariable("postId") Long voteId,
                                                                                     @PathVariable("commentId") Long voteCommentId) {
        log.info("Delete VoteComment Controller 진입");
        voteCommentService.deleteVoteComment(voteId, voteCommentId);
        log.info("Delete VoteComment Controller 종료");

        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.ACCEPTED.value(), DELETE_VOTE_COMMENT_SUCCESS.getMessage()));
    }

    @PutMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "투표해요 게시글의 댓글 수정")
    public ResponseEntity<ResponseDto<VoteCommentUpdateResponse>> updateVoteComment(@PathVariable("postId") Long voteId,
                                                                                              @PathVariable("commentId") Long voteCommentId,
                                                                                              @RequestBody @Valid VoteCommentUpdateRequest req) {
        VoteCommentUpdateResponse result = voteCommentService.updateVoteComment(voteId, voteCommentId, req);
        return ResponseEntity.ok(ResponseDto.update(HttpStatus.ACCEPTED.value(), MODIFY_VOTE_COMMENT_SUCCESS.getMessage(), result));
    }

    @PostMapping("/{postId}/comments{commentId}")
    @Operation(summary = "투표해요 게시글 댓글 좋아요", description = "사용자가 게시글 댓글 좋아요를 누릅니다.")
    public ResponseEntity<ResponseDto<String>> likeVoteComment(@PathVariable("postId") Long voteId,
                                                                    @PathVariable("commentId") Long voteCommentId) {
        log.info("Like VoteComment Controller 진입");
        String result = voteCommentService.updateLikeOfVoteComment(voteId, voteCommentId);
        log.info("Like VoteComment Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LIKE_VOTE_COMMENT_SUCCESS.getMessage(), result));
    }
}