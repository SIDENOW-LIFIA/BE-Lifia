package com.sidenow.domain.together.coBuying.comment.controller;

import com.sidenow.domain.together.coBuying.comment.dto.req.CoBuyingCommentRequest.CoBuyingCommentCreateRequest;
import com.sidenow.domain.together.coBuying.comment.dto.req.CoBuyingCommentRequest.CoBuyingCommentUpdateRequest;
import com.sidenow.domain.together.coBuying.comment.dto.res.CoBuyingCommentResponse.CoBuyingGetCommentListResponse;
import com.sidenow.domain.together.coBuying.comment.service.CoBuyingCommentService;
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

import static com.sidenow.domain.together.coBuying.comment.constant.CoBuyingCommentConstants.CoBuyingCommentSuccessMessage.*;
import static com.sidenow.domain.together.coBuying.comment.dto.res.CoBuyingCommentResponse.CoBuyingCommentCreateResponse;
import static com.sidenow.domain.together.coBuying.comment.dto.res.CoBuyingCommentResponse.CoBuyingCommentUpdateResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board/coBuying")
@Tag(name = "공구해요 댓글 API", description = "CoBuyingComment")
public class CoBuyingCommentController {

    private final CoBuyingCommentService coBuyingCommentService;

    @PostMapping("/{postId}/comments")
    @Operation(summary = "공구해요 게시글의 댓글 작성")
    public ResponseEntity<ResponseDto<CoBuyingCommentCreateResponse>> createCoBuyingComment(@PathVariable("postId") Long coBuyingId,
                                                                                     @RequestBody @Valid CoBuyingCommentCreateRequest req) {
        log.info("Create CoBuying Comment Api Start");
        CoBuyingCommentCreateResponse result = coBuyingCommentService.createCoBuyingComment(coBuyingId, req);
        log.info("Create CoBuying Comment Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_CO_BUYING_COMMENT_SUCCESS.getMessage(), result));
    }

    @GetMapping("/{postId}/comments")
    @Operation(summary = "공구해요 게시글의 댓글 전체 조회")
    public ResponseEntity<ResponseDto<List<CoBuyingGetCommentListResponse>>> readCoBuyingComments(@PathVariable("postId") Long coBuyingId) {
        log.info("Read CoBuying Comments Api Start");
        List<CoBuyingGetCommentListResponse> readCoBuyingComments = coBuyingCommentService.getCoBuyingCommentList(coBuyingId);
        log.info("Read CoBuying Comments Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), READ_CO_BUYING_COMMENT_SUCCESS.getMessage(), readCoBuyingComments));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "공구해요 게시글의 댓글 삭제")
    public ResponseEntity<ResponseDto> deleteCoBuyingComment(@PathVariable("postId") Long coBuyingId,
                                                                                     @PathVariable("commentId") Long coBuyingCommentId) {
        log.info("Delete CoBuyingComment Controller 진입");
        coBuyingCommentService.deleteCoBuyingComment(coBuyingId, coBuyingCommentId);
        log.info("Delete CoBuyingComment Controller 종료");

        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.ACCEPTED.value(), DELETE_CO_BUYING_COMMENT_SUCCESS.getMessage()));
    }

    @PutMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "공구해요 게시글의 댓글 수정")
    public ResponseEntity<ResponseDto<CoBuyingCommentUpdateResponse>> updateCoBuyingComment(@PathVariable("postId") Long coBuyingId,
                                                                                              @PathVariable("commentId") Long coBuyingCommentId,
                                                                                              @RequestBody @Valid CoBuyingCommentUpdateRequest req) {
        CoBuyingCommentUpdateResponse result = coBuyingCommentService.updateCoBuyingComment(coBuyingId, coBuyingCommentId, req);
        return ResponseEntity.ok(ResponseDto.update(HttpStatus.ACCEPTED.value(), MODIFY_CO_BUYING_COMMENT_SUCCESS.getMessage(), result));
    }

    @PostMapping("/{postId}/comments{commentId}")
    @Operation(summary = "공구해요 게시글 댓글 좋아요", description = "사용자가 게시글 댓글 좋아요를 누릅니다.")
    public ResponseEntity<ResponseDto<String>> likeCoBuyingComment(@PathVariable("postId") Long coBuyingId,
                                                                    @PathVariable("commentId") Long coBuyingCommentId) {
        log.info("Like CoBuyingComment Controller 진입");
        String result = coBuyingCommentService.updateLikeOfCoBuyingComment(coBuyingId, coBuyingCommentId);
        log.info("Like CoBuyingComment Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LIKE_CO_BUYING_COMMENT_SUCCESS.getMessage(), result));
    }
}