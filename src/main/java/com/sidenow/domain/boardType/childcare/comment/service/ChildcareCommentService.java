package com.sidenow.domain.boardType.childcare.comment.service;

import com.sidenow.domain.boardType.childcare.comment.dto.req.ChildcareCommentRequest;
import com.sidenow.domain.boardType.childcare.comment.dto.req.ChildcareCommentRequest.ChildcareCommentCreateRequest;
import com.sidenow.domain.boardType.childcare.comment.dto.res.ChildcareCommentResponse.ChildcareGetCommentListResponse;

import java.util.List;

import static com.sidenow.domain.boardType.childcare.comment.dto.res.ChildcareCommentResponse.ChildcareCommentCreateResponse;
import static com.sidenow.domain.boardType.childcare.comment.dto.res.ChildcareCommentResponse.ChildcareCommentUpdateResponse;

public interface ChildcareCommentService {
    // 육아해요 댓글 등록
    ChildcareCommentCreateResponse createChildcareComment(Long childcareId, ChildcareCommentCreateRequest req);
    // 육아해요 댓글 전체 조회
    List<ChildcareGetCommentListResponse> getChildcareCommentList(Long childcareId);
    // 육아해요 댓글 삭제
    void deleteChildcareComment(Long childcareId, Long childcareCommentId);
    // 육아해요 댓글 수정
    ChildcareCommentUpdateResponse updateChildcareComment(Long childcareId, Long childcareCommentId, ChildcareCommentRequest.ChildcareCommentUpdateRequest req);
    // 육아해요 댓글 좋아요
    String updateLikeOfChildcareComment(Long childcareId, Long childcareCommentId);
}
