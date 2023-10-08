package com.sidenow.domain.boardType.free.comment.controller;

import com.sidenow.domain.boardType.free.comment.dto.req.FreeBoardCommentRequest;
import com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse.FreeBoardCommentCheck;
import com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse.FreeBoardGetCommentListResponse;
import com.sidenow.domain.boardType.free.comment.service.FreeBoardCommentServiceImpl;
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

import static com.sidenow.domain.boardType.free.comment.constant.FreeBoardCommentConstants.FreeBoardCommentSuccessMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board/free")
@Tag(name = "FreeBoard Comment API", description = "자유게시판 게시글의 댓글 API 입니다.")
public class FreeBoardCommentController {

    private final FreeBoardCommentServiceImpl freeBoardCommentService;

    @PostMapping("/{postId}/comments")
    @Operation(summary = "자유게시판 게시글의 댓글 작성")
    public ResponseEntity<ResponseDto<FreeBoardCommentCheck>> createFreeBoardComment(@PathVariable("postId") Long freeBoardPostId,
                                                                                     @RequestBody @Valid FreeBoardCommentRequest.RegisterFreeBoardCommentRequest createFreeBoardCommentRequest) {
        log.info("Create FreeBoard Comment Api Start");
        FreeBoardCommentCheck freeBoardCommentCheck = freeBoardCommentService.registerFreeBoardComment(freeBoardPostId, createFreeBoardCommentRequest);
        log.info("Create FreeBoard Comment Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_FREE_BOARD_COMMENT_SUCCESS.getMessage(), freeBoardCommentCheck));
    }

    @GetMapping("/{postId}/comments")
    @Operation(summary = "자유게시판 게시글의 댓글 전체 조회")
    public ResponseEntity<ResponseDto<List<FreeBoardGetCommentListResponse>>> readFreeBoardComments(@PathVariable("postId") Long freeBoardPostId) {
        log.info("Read FreeBoard Comments Api Start");
        List<FreeBoardGetCommentListResponse> readFreeBoardComments = freeBoardCommentService.getFreeBoardCommentList(freeBoardPostId);
        log.info("Read FreeBoard Comments Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), READ_FREE_BOARD_COMMENT_SUCCESS.getMessage(), readFreeBoardComments));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "자유게시판 게시글의 댓글 삭제")
    public ResponseEntity<ResponseDto<FreeBoardCommentCheck>> deleteFreeBoardComment(@PathVariable("postId") Long freeBoardPostId,
                                                                                     @PathVariable("commentId") Long freeBoardCommentId) {
        FreeBoardCommentCheck freeBoardCommentCheck = freeBoardCommentService.deleteFreeBoardComment(freeBoardPostId, freeBoardCommentId);
        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.ACCEPTED.value(), DELETE_FREE_BOARD_COMMENT_SUCCESS.getMessage(), freeBoardCommentCheck));
    }

    @PutMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "자유게시판 게시글의 댓글 수정")
    public ResponseEntity<ResponseDto<FreeBoardCommentCheck>> modifyFreeBoardComment(@PathVariable("postId") Long freeBoardPostId,
                                                                                     @PathVariable("commentId") Long freeBoardCommentId,
                                                                                     @RequestBody @Valid FreeBoardCommentRequest.RegisterFreeBoardCommentRequest createFreeBoardCommentRequest) {
        FreeBoardCommentCheck freeBoardCommentCheck = freeBoardCommentService.modifyFreeBoardComment(freeBoardPostId, freeBoardCommentId, createFreeBoardCommentRequest);
        return ResponseEntity.ok(ResponseDto.update(HttpStatus.ACCEPTED.value(), MODIFY_FREE_BOARD_COMMENT_SUCCESS.getMessage(), freeBoardCommentCheck));
    }
}
