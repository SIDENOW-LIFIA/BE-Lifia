package com.sidenow.domain.boardType.vote.board.exception;

import static com.sidenow.domain.boardType.vote.board.exception.constant.VoteExceptionList.VOTE_LIKE_HISTORY_NOT_FOUND_ERROR;

public class VoteLikeHistoryNotFoundException extends VoteException {
    public VoteLikeHistoryNotFoundException() {
        super(VOTE_LIKE_HISTORY_NOT_FOUND_ERROR.getErrorCode(),
                VOTE_LIKE_HISTORY_NOT_FOUND_ERROR.getHttpStatus(),
                VOTE_LIKE_HISTORY_NOT_FOUND_ERROR.getMessage());
    }
}
