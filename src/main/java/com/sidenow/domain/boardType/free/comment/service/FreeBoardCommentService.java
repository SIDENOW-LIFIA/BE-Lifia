package com.sidenow.domain.boardType.free.comment.service;

import com.sidenow.domain.boardType.free.comment.dto.req.FreeBoardCommentRequest.RegisterFreeBoardCommentRequest;
import com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse.FreeBoardCommentCheck;
import com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse.FreeBoardGetCommentListResponse;

import java.util.List;

public interface FreeBoardCommentService {
    // 자유게시판 댓글 등록
    FreeBoardCommentCheck registerFreeBoardComment(Long freeBoardPostId, RegisterFreeBoardCommentRequest registerFreeBoardCommentRequest);
    // 자유게시판 댓글 전체 조회
    List<FreeBoardGetCommentListResponse> getFreeBoardCommentList(Long freeBoardPostId);
    // 자유게시판 댓글 삭제
    FreeBoardCommentCheck deleteFreeBoardComment(Long freeBoardPostId, Long freeBoardCommentId);
    // 자유게시판 댓글 수정
    FreeBoardCommentCheck modifyFreeBoardComment(Long freeBoardPostId, Long freeBoardCommentId, RegisterFreeBoardCommentRequest registerFreeBoardCommentRequest);
}
