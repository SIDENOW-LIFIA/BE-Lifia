package com.sidenow.domain.together.vote.comment.exception;

import static com.sidenow.domain.together.vote.comment.exception.constant.VoteCommentExceptionList.VOTE_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR;

public class VoteCommentLikeHistoryNotFoundException extends VoteCommentException {
    public VoteCommentLikeHistoryNotFoundException() {
        super(VOTE_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getErrorCode(),
                VOTE_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getHttpStatus(),
                VOTE_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR.getMessage());
    }
}
