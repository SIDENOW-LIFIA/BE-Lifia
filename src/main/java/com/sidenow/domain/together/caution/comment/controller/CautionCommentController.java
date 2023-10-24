package com.sidenow.domain.together.caution.comment.controller;

import com.sidenow.domain.together.caution.comment.service.CautionCommentService;
import com.sidenow.domain.together.caution.comment.dto.req.CautionCommentRequest.CautionCommentCreateRequest;
import com.sidenow.domain.together.caution.comment.dto.req.CautionCommentRequest.CautionCommentUpdateRequest;
import com.sidenow.domain.together.caution.comment.dto.res.CautionCommentResponse.CautionGetCommentListResponse;
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

import static com.sidenow.domain.together.caution.comment.constant.CautionCommentConstants.CautionCommentSuccessMessage.*;
import static com.sidenow.domain.together.caution.comment.dto.res.CautionCommentResponse.CautionCommentCreateResponse;
import static com.sidenow.domain.together.caution.comment.dto.res.CautionCommentResponse.CautionCommentUpdateResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board/caution")
@Tag(name = "조심해요 댓글 API", description = "CautionComment")
public class CautionCommentController {

    private final CautionCommentService cautionCommentService;

    @PostMapping("/{postId}/comments")
    @Operation(summary = "조심해요 게시글의 댓글 작성")
    public ResponseEntity<ResponseDto<CautionCommentCreateResponse>> createCautionComment(@PathVariable("postId") Long cautionId,
                                                                                     @RequestBody @Valid CautionCommentCreateRequest req) {
        log.info("Create Caution Comment Api Start");
        CautionCommentCreateResponse result = cautionCommentService.createCautionComment(cautionId, req);
        log.info("Create Caution Comment Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_CAUTION_COMMENT_SUCCESS.getMessage(), result));
    }

    @GetMapping("/{postId}/comments")
    @Operation(summary = "조심해요 게시글의 댓글 전체 조회")
    public ResponseEntity<ResponseDto<List<CautionGetCommentListResponse>>> readCautionComments(@PathVariable("postId") Long cautionId) {
        log.info("Read Caution Comments Api Start");
        List<CautionGetCommentListResponse> readCautionComments = cautionCommentService.getCautionCommentList(cautionId);
        log.info("Read Caution Comments Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), READ_CAUTION_COMMENT_SUCCESS.getMessage(), readCautionComments));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "조심해요 게시글의 댓글 삭제")
    public ResponseEntity<ResponseDto> deleteCautionComment(@PathVariable("postId") Long cautionId,
                                                                                     @PathVariable("commentId") Long cautionCommentId) {
        log.info("Delete CautionComment Controller 진입");
        cautionCommentService.deleteCautionComment(cautionId, cautionCommentId);
        log.info("Delete CautionComment Controller 종료");

        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.ACCEPTED.value(), DELETE_CAUTION_COMMENT_SUCCESS.getMessage()));
    }

    @PutMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "조심해요 게시글의 댓글 수정")
    public ResponseEntity<ResponseDto<CautionCommentUpdateResponse>> updateCautionComment(@PathVariable("postId") Long cautionId,
                                                                                              @PathVariable("commentId") Long cautionCommentId,
                                                                                              @RequestBody @Valid CautionCommentUpdateRequest req) {
        CautionCommentUpdateResponse result = cautionCommentService.updateCautionComment(cautionId, cautionCommentId, req);
        return ResponseEntity.ok(ResponseDto.update(HttpStatus.ACCEPTED.value(), MODIFY_CAUTION_COMMENT_SUCCESS.getMessage(), result));
    }

    @PostMapping("/{postId}/comments{commentId}")
    @Operation(summary = "조심해요 게시글 댓글 좋아요", description = "사용자가 게시글 댓글 좋아요를 누릅니다.")
    public ResponseEntity<ResponseDto<String>> likeCautionComment(@PathVariable("postId") Long cautionId,
                                                                    @PathVariable("commentId") Long cautionCommentId) {
        log.info("Like CautionComment Controller 진입");
        String result = cautionCommentService.updateLikeOfCautionComment(cautionId, cautionCommentId);
        log.info("Like CautionComment Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LIKE_CAUTION_COMMENT_SUCCESS.getMessage(), result));
    }
}