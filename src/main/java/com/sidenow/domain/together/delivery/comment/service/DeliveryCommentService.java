package com.sidenow.domain.together.delivery.comment.service;

import com.sidenow.domain.together.delivery.comment.dto.req.DeliveryCommentRequest.DeliveryCommentCreateRequest;
import com.sidenow.domain.together.delivery.comment.dto.res.DeliveryCommentResponse.DeliveryGetCommentListResponse;
import com.sidenow.domain.together.delivery.comment.dto.req.DeliveryCommentRequest;

import java.util.List;

import static com.sidenow.domain.together.delivery.comment.dto.res.DeliveryCommentResponse.DeliveryCommentCreateResponse;
import static com.sidenow.domain.together.delivery.comment.dto.res.DeliveryCommentResponse.DeliveryCommentUpdateResponse;

public interface DeliveryCommentService {
    // 배달해요 댓글 등록
    DeliveryCommentCreateResponse createDeliveryComment(Long deliveryId, DeliveryCommentCreateRequest req);
    // 배달해요 댓글 전체 조회
    List<DeliveryGetCommentListResponse> getDeliveryCommentList(Long deliveryId);
    // 배달해요 댓글 삭제
    void deleteDeliveryComment(Long deliveryId, Long deliveryCommentId);
    // 배달해요 댓글 수정
    DeliveryCommentUpdateResponse updateDeliveryComment(Long deliveryId, Long deliveryCommentId, DeliveryCommentRequest.DeliveryCommentUpdateRequest req);
    // 배달해요 댓글 좋아요
    String updateLikeOfDeliveryComment(Long deliveryId, Long deliveryCommentId);
}
