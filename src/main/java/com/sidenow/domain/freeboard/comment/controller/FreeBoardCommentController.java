package com.sidenow.domain.freeboard.comment.controller;

import com.sidenow.domain.freeboard.comment.dto.req.FreeBoardCommentRequest.FreeBoardCommentCreateRequest;
import com.sidenow.domain.freeboard.comment.dto.req.FreeBoardCommentRequest.FreeBoardCommentUpdateRequest;
import com.sidenow.domain.freeboard.comment.dto.res.FreeBoardCommentResponse.FreeBoardGetCommentListResponse;
import com.sidenow.domain.freeboard.comment.service.FreeBoardCommentService;
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

import static com.sidenow.domain.freeboard.comment.constant.FreeBoardCommentConstants.FreeBoardCommentSuccessMessage.*;
import static com.sidenow.domain.freeboard.comment.dto.res.FreeBoardCommentResponse.FreeBoardCommentCreateResponse;
import static com.sidenow.domain.freeboard.comment.dto.res.FreeBoardCommentResponse.FreeBoardCommentUpdateResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board/free")
@Tag(name = "자유게시판 댓글 API", description = "FreeBoardComment")
public class FreeBoardCommentController {

    private final FreeBoardCommentService freeBoardCommentService;

    @PostMapping("/{postId}/comments")
    @Operation(summary = "자유게시판 게시글의 댓글 작성")
    public ResponseEntity<ResponseDto<FreeBoardCommentCreateResponse>> createFreeBoardComment(@PathVariable("postId") Long freeBoardId,
                                                                                     @RequestBody @Valid FreeBoardCommentCreateRequest req) {
        log.info("Create FreeBoard Comment Api Start");
        FreeBoardCommentCreateResponse result = freeBoardCommentService.createFreeBoardComment(freeBoardId, req);
        log.info("Create FreeBoard Comment Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_FREE_BOARD_COMMENT_SUCCESS.getMessage(), result));
    }

    @GetMapping("/{postId}/comments")
    @Operation(summary = "자유게시판 게시글의 댓글 전체 조회")
    public ResponseEntity<ResponseDto<List<FreeBoardGetCommentListResponse>>> readFreeBoardComments(@PathVariable("postId") Long freeBoardId) {
        log.info("Read FreeBoard Comments Api Start");
        List<FreeBoardGetCommentListResponse> readFreeBoardComments = freeBoardCommentService.getFreeBoardCommentList(freeBoardId);
        log.info("Read FreeBoard Comments Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), READ_FREE_BOARD_COMMENT_SUCCESS.getMessage(), readFreeBoardComments));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "자유게시판 게시글의 댓글 삭제")
    public ResponseEntity<ResponseDto> deleteFreeBoardComment(@PathVariable("postId") Long freeBoardId,
                                                                                     @PathVariable("commentId") Long freeBoardCommentId) {
        log.info("Delete FreeBoardComment Controller 진입");
        freeBoardCommentService.deleteFreeBoardComment(freeBoardId, freeBoardCommentId);
        log.info("Delete FreeBoardComment Controller 종료");

        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.ACCEPTED.value(), DELETE_FREE_BOARD_COMMENT_SUCCESS.getMessage()));
    }

    @PutMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "자유게시판 게시글의 댓글 수정")
    public ResponseEntity<ResponseDto<FreeBoardCommentUpdateResponse>> updateFreeBoardComment(@PathVariable("postId") Long freeBoardId,
                                                                                              @PathVariable("commentId") Long freeBoardCommentId,
                                                                                              @RequestBody @Valid FreeBoardCommentUpdateRequest req) {
        FreeBoardCommentUpdateResponse result = freeBoardCommentService.updateFreeBoardComment(freeBoardId, freeBoardCommentId, req);
        return ResponseEntity.ok(ResponseDto.update(HttpStatus.ACCEPTED.value(), MODIFY_FREE_BOARD_COMMENT_SUCCESS.getMessage(), result));
    }

    @PostMapping("/{postId}/comments{commentId}")
    @Operation(summary = "자유게시판 게시글 댓글 좋아요", description = "사용자가 게시글 댓글 좋아요를 누릅니다.")
    public ResponseEntity<ResponseDto<String>> likeFreeBoardComment(@PathVariable("postId") Long freeBoardId,
                                                                    @PathVariable("commentId") Long freeBoardCommentId) {
        log.info("Like FreeBoardComment Controller 진입");
        String result = freeBoardCommentService.updateLikeOfFreeBoardComment(freeBoardId, freeBoardCommentId);
        log.info("Like FreeBoardComment Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LIKE_FREE_BOARD_COMMENT_SUCCESS.getMessage(), result));
    }
}