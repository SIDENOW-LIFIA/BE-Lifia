package com.sidenow.domain.boardType.free.comment.service;

import com.sidenow.domain.boardType.free.comment.dto.req.FreeBoardCommentRequest.RegisterFreeBoardCommentRequest;
import com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse;
import com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse.FreeBoardCommentCheck;
import com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse.FreeBoardGetCommentListResponse;
import java.util.List;

import static com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse.*;

public interface FreeBoardCommentService {
    // 자유게시판 댓글 등록
    FreeBoardCommentCreateResponse createFreeBoardComment(Long freeBoardId, RegisterFreeBoardCommentRequest req);
    // 자유게시판 댓글 전체 조회
    List<FreeBoardGetCommentListResponse> getFreeBoardCommentList(Long freeBoardId);
    // 자유게시판 댓글 삭제
    FreeBoardCommentCheck deleteFreeBoardComment(Long freeBoardId, Long freeBoardCommentId);
    // 자유게시판 댓글 수정
    FreeBoardCommentCheck modifyFreeBoardComment(Long freeBoardId, Long freeBoardCommentId, RegisterFreeBoardCommentRequest req);
}
