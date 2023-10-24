package com.sidenow.domain.boardType.childcare.comment.controller;

import com.sidenow.domain.boardType.childcare.comment.service.ChildcareCommentService;
import com.sidenow.domain.boardType.childcare.comment.dto.req.ChildcareCommentRequest.ChildcareCommentCreateRequest;
import com.sidenow.domain.boardType.childcare.comment.dto.req.ChildcareCommentRequest.ChildcareCommentUpdateRequest;
import com.sidenow.domain.boardType.childcare.comment.dto.res.ChildcareCommentResponse.ChildcareGetCommentListResponse;
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

import static com.sidenow.domain.boardType.childcare.comment.constant.ChildcareCommentConstants.ChildcareCommentSuccessMessage.*;
import static com.sidenow.domain.boardType.childcare.comment.dto.res.ChildcareCommentResponse.ChildcareCommentCreateResponse;
import static com.sidenow.domain.boardType.childcare.comment.dto.res.ChildcareCommentResponse.ChildcareCommentUpdateResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board/childcare")
@Tag(name = "Childcare Comment API", description = "육아해요 게시글의 댓글 API 입니다.")
public class ChildcareCommentController {

    private final ChildcareCommentService childcareCommentService;

    @PostMapping("/{postId}/comments")
    @Operation(summary = "육아해요 게시글의 댓글 작성")
    public ResponseEntity<ResponseDto<ChildcareCommentCreateResponse>> createChildcareComment(@PathVariable("postId") Long childcareId,
                                                                                     @RequestBody @Valid ChildcareCommentCreateRequest req) {
        log.info("Create Childcare Comment Api Start");
        ChildcareCommentCreateResponse result = childcareCommentService.createChildcareComment(childcareId, req);
        log.info("Create Childcare Comment Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_CHILDCARE_COMMENT_SUCCESS.getMessage(), result));
    }

    @GetMapping("/{postId}/comments")
    @Operation(summary = "육아해요 게시글의 댓글 전체 조회")
    public ResponseEntity<ResponseDto<List<ChildcareGetCommentListResponse>>> readChildcareComments(@PathVariable("postId") Long childcareId) {
        log.info("Read Childcare Comments Api Start");
        List<ChildcareGetCommentListResponse> readChildcareComments = childcareCommentService.getChildcareCommentList(childcareId);
        log.info("Read Childcare Comments Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), READ_CHILDCARE_COMMENT_SUCCESS.getMessage(), readChildcareComments));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "육아해요 게시글의 댓글 삭제")
    public ResponseEntity<ResponseDto> deleteChildcareComment(@PathVariable("postId") Long childcareId,
                                                                                     @PathVariable("commentId") Long childcareCommentId) {
        log.info("Delete ChildcareComment Controller 진입");
        childcareCommentService.deleteChildcareComment(childcareId, childcareCommentId);
        log.info("Delete ChildcareComment Controller 종료");

        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.ACCEPTED.value(), DELETE_CHILDCARE_COMMENT_SUCCESS.getMessage()));
    }

    @PutMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "육아해요 게시글의 댓글 수정")
    public ResponseEntity<ResponseDto<ChildcareCommentUpdateResponse>> updateChildcareComment(@PathVariable("postId") Long childcareId,
                                                                                              @PathVariable("commentId") Long childcareCommentId,
                                                                                              @RequestBody @Valid ChildcareCommentUpdateRequest req) {
        ChildcareCommentUpdateResponse result = childcareCommentService.updateChildcareComment(childcareId, childcareCommentId, req);
        return ResponseEntity.ok(ResponseDto.update(HttpStatus.ACCEPTED.value(), MODIFY_CHILDCARE_COMMENT_SUCCESS.getMessage(), result));
    }

    @PostMapping("/{postId}/comments{commentId}")
    @Operation(summary = "육아해요 게시글 댓글 좋아요", description = "사용자가 게시글 댓글 좋아요를 누릅니다.")
    public ResponseEntity<ResponseDto<String>> likeChildcareComment(@PathVariable("postId") Long childcareId,
                                                                    @PathVariable("commentId") Long childcareCommentId) {
        log.info("Like ChildcareComment Controller 진입");
        String result = childcareCommentService.updateLikeOfChildcareComment(childcareId, childcareCommentId);
        log.info("Like ChildcareComment Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LIKE_CHILDCARE_COMMENT_SUCCESS.getMessage(), result));
    }
}