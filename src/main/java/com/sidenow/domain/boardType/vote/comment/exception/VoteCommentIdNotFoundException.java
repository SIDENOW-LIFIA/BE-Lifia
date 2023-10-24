package com.sidenow.domain.boardType.vote.comment.exception;

import static com.sidenow.domain.boardType.vote.comment.exception.constant.VoteCommentExceptionList.VOTE_COMMENT_ID_NOT_FOUND_ERROR;

public class VoteCommentIdNotFoundException extends VoteCommentException {
    public VoteCommentIdNotFoundException() {
        super(VOTE_COMMENT_ID_NOT_FOUND_ERROR.getErrorCode(),
                VOTE_COMMENT_ID_NOT_FOUND_ERROR.getHttpStatus(),
                VOTE_COMMENT_ID_NOT_FOUND_ERROR.getMessage());
    }
}
