package com.sidenow.domain.together.vote.comment.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class VoteCommentConstants {

    @Getter
    @RequiredArgsConstructor
    public enum VoteCommentSuccessMessage {
        READ_VOTE_COMMENT_SUCCESS("투표해요 댓글 조회에 성공했습니다"),
        CREATE_VOTE_COMMENT_SUCCESS("투표해요 댓글 생성에 성공했습니다"),
        DELETE_VOTE_COMMENT_SUCCESS("투표해요 댓글 삭제에 성공했습니다"),
        MODIFY_VOTE_COMMENT_SUCCESS("투표해요 댓글 수정에 성공했습니다"),
        LIKE_VOTE_COMMENT_SUCCESS("좋아요 처리가 완료되었습니다.");;
        private final String message;
    }

}
