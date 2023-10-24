package com.sidenow.domain.together.coBuying.comment.service;

import com.sidenow.domain.together.coBuying.comment.dto.req.CoBuyingCommentRequest.CoBuyingCommentCreateRequest;
import com.sidenow.domain.together.coBuying.comment.dto.res.CoBuyingCommentResponse.CoBuyingGetCommentListResponse;
import com.sidenow.domain.together.coBuying.comment.dto.req.CoBuyingCommentRequest;

import java.util.List;

import static com.sidenow.domain.together.coBuying.comment.dto.res.CoBuyingCommentResponse.CoBuyingCommentCreateResponse;
import static com.sidenow.domain.together.coBuying.comment.dto.res.CoBuyingCommentResponse.CoBuyingCommentUpdateResponse;

public interface CoBuyingCommentService {
    // 공구해요 댓글 등록
    CoBuyingCommentCreateResponse createCoBuyingComment(Long coBuyingId, CoBuyingCommentCreateRequest req);
    // 공구해요 댓글 전체 조회
    List<CoBuyingGetCommentListResponse> getCoBuyingCommentList(Long coBuyingId);
    // 공구해요 댓글 삭제
    void deleteCoBuyingComment(Long coBuyingId, Long coBuyingCommentId);
    // 공구해요 댓글 수정
    CoBuyingCommentUpdateResponse updateCoBuyingComment(Long coBuyingId, Long coBuyingCommentId, CoBuyingCommentRequest.CoBuyingCommentUpdateRequest req);
    // 공구해요 댓글 좋아요
    String updateLikeOfCoBuyingComment(Long coBuyingId, Long coBuyingCommentId);
}
