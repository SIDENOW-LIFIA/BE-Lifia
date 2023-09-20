package com.sidenow.domain.boardType.childCare.comment.controller;

import com.sidenow.domain.boardType.childCare.comment.dto.req.ChildCareBoardCommentRequest;
import com.sidenow.domain.boardType.childCare.comment.dto.res.ChildCareBoardCommentResponse.ChildCareBoardCommentCheck;
import com.sidenow.domain.boardType.childCare.comment.dto.res.ChildCareBoardCommentResponse.ChildCareBoardGetCommentListResponse;
import com.sidenow.domain.boardType.childCare.comment.service.ChildCareBoardCommentServiceImpl;
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

import static com.sidenow.domain.boardType.childCare.comment.constant.ChildCareBoardCommentConstants.ChildCareBoardCommentSuccessMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/board/childCare")
@Tag(name = "ChildCareBoard Comment API", description = "육아게시판 게시글의 댓글 API 입니다.")
public class ChildCareBoardCommentController {

    private final ChildCareBoardCommentServiceImpl childCareBoardCommentService;

    @PostMapping("/{postId}/comments")
    @Operation(summary = "육아게시판 게시글의 댓글 작성")
    public ResponseEntity<ResponseDto<ChildCareBoardCommentCheck>> createChildCareBoardComment(@PathVariable("postId") Long childCareBoardPostId,
                                                                                     @RequestBody @Valid ChildCareBoardCommentRequest.RegisterChildCareBoardCommentRequest createChildCareBoardCommentRequest) {
        log.info("Create ChildCareBoard Comment Api Start");
        ChildCareBoardCommentCheck childCareBoardCommentCheck = childCareBoardCommentService.registerChildCareBoardComment(childCareBoardPostId, createChildCareBoardCommentRequest);
        log.info("Create ChildCareBoard Comment Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_CHILDCARE_BOARD_COMMENT_SUCCESS.getMessage(), childCareBoardCommentCheck));
    }

    @GetMapping("/{postId}/comments")
    @Operation(summary = "육아게시판 게시글의 댓글 전체 조회")
    public ResponseEntity<ResponseDto<List<ChildCareBoardGetCommentListResponse>>> readChildCareBoardComments(@PathVariable("postId") Long childCareBoardPostId) {
        log.info("Read ChildCareBoard Comments Api Start");
        List<ChildCareBoardGetCommentListResponse> readChildCareBoardComments = childCareBoardCommentService.getChildCareBoardCommentList(childCareBoardPostId);
        log.info("Read ChildCareBoard Comments Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), READ_CHILDCARE_BOARD_COMMENT_SUCCESS.getMessage(), readChildCareBoardComments));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "육아게시판 게시글의 댓글 삭제")
    public ResponseEntity<ResponseDto<ChildCareBoardCommentCheck>> deleteChildCareBoardComment(@PathVariable("postId") Long childCareBoardPostId,
                                                                                     @PathVariable("commentId") Long childCareBoardCommentId) {
        ChildCareBoardCommentCheck childCareBoardCommentCheck = childCareBoardCommentService.deleteChildCareBoardComment(childCareBoardPostId, childCareBoardCommentId);
        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.ACCEPTED.value(), DELETE_CHILDCARE_BOARD_COMMENT_SUCCESS.getMessage(), childCareBoardCommentCheck));
    }

    @PutMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "육아게시판 게시글의 댓글 수정")
    public ResponseEntity<ResponseDto<ChildCareBoardCommentCheck>> modifyChildCareBoardComment(@PathVariable("postId") Long childCareBoardPostId,
                                                                                     @PathVariable("commentId") Long childCareBoardCommentId,
                                                                                     @RequestBody @Valid ChildCareBoardCommentRequest.RegisterChildCareBoardCommentRequest createChildCareBoardCommentRequest) {
        ChildCareBoardCommentCheck childCareBoardCommentCheck = childCareBoardCommentService.modifyChildCareBoardComment(childCareBoardPostId, childCareBoardCommentId, createChildCareBoardCommentRequest);
        return ResponseEntity.ok(ResponseDto.update(HttpStatus.ACCEPTED.value(), MODIFY_CHILDCARE_BOARD_COMMENT_SUCCESS.getMessage(), childCareBoardCommentCheck));
    }
}
