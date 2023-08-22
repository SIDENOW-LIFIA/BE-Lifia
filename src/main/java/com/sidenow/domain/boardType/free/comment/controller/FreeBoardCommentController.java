package com.sidenow.domain.boardType.free.comment.controller;

import com.amazonaws.Response;
import com.sidenow.domain.boardType.free.comment.dto.req.FreeBoardCommentRequest.CreateFreeBoardCommentRequest;
import com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse;
import com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse.CreateFreeBoardCommentResponse;
import com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse.ReadFreeBoardCommentDetailResponse;
import com.sidenow.domain.boardType.free.comment.service.FreeBoardCommentService;
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

import static com.sidenow.domain.boardType.free.comment.constant.FreeBoardCommentConstants.FreeBoardCommentSuccessMessage.CREATE_FREE_BOARD_COMMENT_SUCCESS;
import static com.sidenow.domain.boardType.free.comment.constant.FreeBoardCommentConstants.FreeBoardCommentSuccessMessage.READ_FREE_BOARD_COMMENT_SUCCESS;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/board/free")
@Tag(name = "FreeBoard Comment API", description = "자유게시판 게시글의 댓글 API 입니다.")
public class FreeBoardCommentController {

    private final FreeBoardCommentService freeBoardCommentService;

    @PostMapping("/{postId}/comments")
    @Operation(summary = "자유게시판 게시글의 댓글 작성")
    public ResponseEntity<ResponseDto<CreateFreeBoardCommentResponse>> createFreeBoardComment(@PathVariable Long freeBoardPostId,
                                                                                              @RequestBody @Valid CreateFreeBoardCommentRequest createFreeBoardCommentRequest) {
        CreateFreeBoardCommentResponse createFreeBoardComment = freeBoardCommentService.createFreeBoardComments(freeBoardPostId, createFreeBoardCommentRequest);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_FREE_BOARD_COMMENT_SUCCESS.getMessage(), createFreeBoardComment));
    }

    @GetMapping("/{postId}/comments")
    @Operation(summary = "자유게시판 게시글의 댓글 조회")
    public ResponseEntity<ResponseDto<FreeBoardCommentResponse.ReadFreeBoardCommentsAllResponse>> readFreeBoardComment(@PathVariable Long freeBoardPostId) {
        FreeBoardCommentResponse.ReadFreeBoardCommentsAllResponse readFreeBoardCommentsAll = freeBoardCommentService.readFreeBoardComments(freeBoardPostId);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), READ_FREE_BOARD_COMMENT_SUCCESS.getMessage(), readFreeBoardCommentsAll));
    }
}
