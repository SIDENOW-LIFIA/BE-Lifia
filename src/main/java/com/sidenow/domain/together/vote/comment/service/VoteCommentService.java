package com.sidenow.domain.together.vote.comment.service;

import com.sidenow.domain.together.vote.comment.dto.req.VoteCommentRequest.VoteCommentCreateRequest;
import com.sidenow.domain.together.vote.comment.dto.res.VoteCommentResponse.VoteGetCommentListResponse;
import com.sidenow.domain.together.vote.comment.dto.req.VoteCommentRequest;

import java.util.List;

import static com.sidenow.domain.together.vote.comment.dto.res.VoteCommentResponse.VoteCommentCreateResponse;
import static com.sidenow.domain.together.vote.comment.dto.res.VoteCommentResponse.VoteCommentUpdateResponse;

public interface VoteCommentService {
    // 투표해요 댓글 등록
    VoteCommentCreateResponse createVoteComment(Long voteId, VoteCommentCreateRequest req);
    // 투표해요 댓글 전체 조회
    List<VoteGetCommentListResponse> getVoteCommentList(Long voteId);
    // 투표해요 댓글 삭제
    void deleteVoteComment(Long voteId, Long voteCommentId);
    // 투표해요 댓글 수정
    VoteCommentUpdateResponse updateVoteComment(Long voteId, Long voteCommentId, VoteCommentRequest.VoteCommentUpdateRequest req);
    // 투표해요 댓글 좋아요
    String updateLikeOfVoteComment(Long voteId, Long voteCommentId);
}
