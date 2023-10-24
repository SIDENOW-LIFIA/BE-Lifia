package com.sidenow.domain.freeboard.comment.service;

import com.sidenow.domain.freeboard.comment.dto.req.FreeBoardCommentRequest;
import com.sidenow.domain.freeboard.comment.dto.req.FreeBoardCommentRequest.FreeBoardCommentCreateRequest;
import com.sidenow.domain.freeboard.comment.dto.res.FreeBoardCommentResponse.FreeBoardGetCommentListResponse;

import java.util.List;

import static com.sidenow.domain.freeboard.comment.dto.res.FreeBoardCommentResponse.FreeBoardCommentCreateResponse;
import static com.sidenow.domain.freeboard.comment.dto.res.FreeBoardCommentResponse.FreeBoardCommentUpdateResponse;

public interface FreeBoardCommentService {
    // 자유게시판 댓글 등록
    FreeBoardCommentCreateResponse createFreeBoardComment(Long freeBoardId, FreeBoardCommentCreateRequest req);
    // 자유게시판 댓글 전체 조회
    List<FreeBoardGetCommentListResponse> getFreeBoardCommentList(Long freeBoardId);
    // 자유게시판 댓글 삭제
    void deleteFreeBoardComment(Long freeBoardId, Long freeBoardCommentId);
    // 자유게시판 댓글 수정
    FreeBoardCommentUpdateResponse updateFreeBoardComment(Long freeBoardId, Long freeBoardCommentId, FreeBoardCommentRequest.FreeBoardCommentUpdateRequest req);
    // 자유게시판 댓글 좋아요
    String updateLikeOfFreeBoardComment(Long freeBoardId, Long freeBoardCommentId);
}
