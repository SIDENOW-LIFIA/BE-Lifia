package com.sidenow.domain.together.vote.board.exception;

import static com.sidenow.domain.together.vote.board.exception.constant.VoteExceptionList.VOTE_ID_NOT_FOUND_ERROR;

public class VoteIdNotFoundException extends VoteException {
    public VoteIdNotFoundException() {
        super(VOTE_ID_NOT_FOUND_ERROR.getErrorCode(),
                VOTE_ID_NOT_FOUND_ERROR.getHttpStatus(),
                VOTE_ID_NOT_FOUND_ERROR.getMessage());
    }
}
