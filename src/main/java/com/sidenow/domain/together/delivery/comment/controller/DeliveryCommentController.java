package com.sidenow.domain.together.delivery.comment.controller;

import com.sidenow.domain.together.delivery.comment.dto.req.DeliveryCommentRequest.DeliveryCommentCreateRequest;
import com.sidenow.domain.together.delivery.comment.dto.req.DeliveryCommentRequest.DeliveryCommentUpdateRequest;
import com.sidenow.domain.together.delivery.comment.dto.res.DeliveryCommentResponse.DeliveryGetCommentListResponse;
import com.sidenow.domain.together.delivery.comment.service.DeliveryCommentService;
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

import static com.sidenow.domain.together.delivery.comment.constant.DeliveryCommentConstants.DeliveryCommentSuccessMessage.*;
import static com.sidenow.domain.together.delivery.comment.dto.res.DeliveryCommentResponse.DeliveryCommentCreateResponse;
import static com.sidenow.domain.together.delivery.comment.dto.res.DeliveryCommentResponse.DeliveryCommentUpdateResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board/delivery")
@Tag(name = "배달해요 댓글 API", description = "DeliveryComment")
public class DeliveryCommentController {

    private final DeliveryCommentService deliveryCommentService;

    @PostMapping("/{postId}/comments")
    @Operation(summary = "배달해요 게시글의 댓글 작성")
    public ResponseEntity<ResponseDto<DeliveryCommentCreateResponse>> createDeliveryComment(@PathVariable("postId") Long deliveryId,
                                                                                     @RequestBody @Valid DeliveryCommentCreateRequest req) {
        log.info("Create Delivery Comment Api Start");
        DeliveryCommentCreateResponse result = deliveryCommentService.createDeliveryComment(deliveryId, req);
        log.info("Create Delivery Comment Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_DELIVERY_COMMENT_SUCCESS.getMessage(), result));
    }

    @GetMapping("/{postId}/comments")
    @Operation(summary = "배달해요 게시글의 댓글 전체 조회")
    public ResponseEntity<ResponseDto<List<DeliveryGetCommentListResponse>>> readDeliveryComments(@PathVariable("postId") Long deliveryId) {
        log.info("Read Delivery Comments Api Start");
        List<DeliveryGetCommentListResponse> readDeliveryComments = deliveryCommentService.getDeliveryCommentList(deliveryId);
        log.info("Read Delivery Comments Api End");
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), READ_DELIVERY_COMMENT_SUCCESS.getMessage(), readDeliveryComments));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "배달해요 게시글의 댓글 삭제")
    public ResponseEntity<ResponseDto> deleteDeliveryComment(@PathVariable("postId") Long deliveryId,
                                                                                     @PathVariable("commentId") Long deliveryCommentId) {
        log.info("Delete DeliveryComment Controller 진입");
        deliveryCommentService.deleteDeliveryComment(deliveryId, deliveryCommentId);
        log.info("Delete DeliveryComment Controller 종료");

        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.ACCEPTED.value(), DELETE_DELIVERY_COMMENT_SUCCESS.getMessage()));
    }

    @PutMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "배달해요 게시글의 댓글 수정")
    public ResponseEntity<ResponseDto<DeliveryCommentUpdateResponse>> updateDeliveryComment(@PathVariable("postId") Long deliveryId,
                                                                                              @PathVariable("commentId") Long deliveryCommentId,
                                                                                              @RequestBody @Valid DeliveryCommentUpdateRequest req) {
        DeliveryCommentUpdateResponse result = deliveryCommentService.updateDeliveryComment(deliveryId, deliveryCommentId, req);
        return ResponseEntity.ok(ResponseDto.update(HttpStatus.ACCEPTED.value(), MODIFY_DELIVERY_COMMENT_SUCCESS.getMessage(), result));
    }

    @PostMapping("/{postId}/comments{commentId}")
    @Operation(summary = "배달해요 게시글 댓글 좋아요", description = "사용자가 게시글 댓글 좋아요를 누릅니다.")
    public ResponseEntity<ResponseDto<String>> likeDeliveryComment(@PathVariable("postId") Long deliveryId,
                                                                    @PathVariable("commentId") Long deliveryCommentId) {
        log.info("Like DeliveryComment Controller 진입");
        String result = deliveryCommentService.updateLikeOfDeliveryComment(deliveryId, deliveryCommentId);
        log.info("Like DeliveryComment Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LIKE_DELIVERY_COMMENT_SUCCESS.getMessage(), result));
    }
}