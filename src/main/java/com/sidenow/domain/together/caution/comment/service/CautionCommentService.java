package com.sidenow.domain.together.caution.comment.service;

import com.sidenow.domain.together.caution.comment.dto.req.CautionCommentRequest;
import com.sidenow.domain.together.caution.comment.dto.req.CautionCommentRequest.CautionCommentCreateRequest;
import com.sidenow.domain.together.caution.comment.dto.res.CautionCommentResponse.CautionGetCommentListResponse;

import java.util.List;

import static com.sidenow.domain.together.caution.comment.dto.res.CautionCommentResponse.CautionCommentCreateResponse;
import static com.sidenow.domain.together.caution.comment.dto.res.CautionCommentResponse.CautionCommentUpdateResponse;

public interface CautionCommentService {
    // 조심해요 댓글 등록
    CautionCommentCreateResponse createCautionComment(Long cautionId, CautionCommentCreateRequest req);
    // 조심해요 댓글 전체 조회
    List<CautionGetCommentListResponse> getCautionCommentList(Long cautionId);
    // 조심해요 댓글 삭제
    void deleteCautionComment(Long cautionId, Long cautionCommentId);
    // 조심해요 댓글 수정
    CautionCommentUpdateResponse updateCautionComment(Long cautionId, Long cautionCommentId, CautionCommentRequest.CautionCommentUpdateRequest req);
    // 조심해요 댓글 좋아요
    String updateLikeOfCautionComment(Long cautionId, Long cautionCommentId);
}
