package com.sidenow.domain.boardType.childCare.comment.service;

import com.sidenow.domain.boardType.childCare.comment.dto.req.ChildCareBoardCommentRequest.RegisterChildCareBoardCommentRequest;
import com.sidenow.domain.boardType.childCare.comment.dto.res.ChildCareBoardCommentResponse.ChildCareBoardCommentCheck;
import com.sidenow.domain.boardType.childCare.comment.dto.res.ChildCareBoardCommentResponse.ChildCareBoardGetCommentListResponse;
import java.util.List;

public interface ChildCareBoardCommentService {
    // 육아게시판 댓글 등록
    ChildCareBoardCommentCheck registerChildCareBoardComment(Long childCareBoardPostId, RegisterChildCareBoardCommentRequest registerChildCareBoardCommentRequest);
    // 육아게시판 댓글 전체 조회
    List<ChildCareBoardGetCommentListResponse> getChildCareBoardCommentList(Long childCareBoardPostId);
    // 육아게시판 댓글 삭제
    ChildCareBoardCommentCheck deleteChildCareBoardComment(Long childCareBoardPostId, Long childCareBoardCommentId);
    // 육아게시판 댓글 수정
    ChildCareBoardCommentCheck modifyChildCareBoardComment(Long childCareBoardPostId, Long childCareBoardCommentId, RegisterChildCareBoardCommentRequest registerChildCareBoardCommentRequest);
}
