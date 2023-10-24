package com.sidenow.domain.together.vote.comment.exception;

import static com.sidenow.domain.together.vote.comment.exception.constant.VoteCommentExceptionList.VOTE_COMMENT_AUTH_ERROR;

public class VoteCommentAuthErrorException extends VoteCommentException {
    public VoteCommentAuthErrorException() {
        super(VOTE_COMMENT_AUTH_ERROR.getErrorCode(),
                VOTE_COMMENT_AUTH_ERROR.getHttpStatus(),
                VOTE_COMMENT_AUTH_ERROR.getMessage());
    }
}
