package com.sidenow.domain.together.vote.board.exception;

import static com.sidenow.domain.together.vote.board.exception.constant.VoteExceptionList.VOTE_MEMBER_NOT_FOUND_ERROR;

public class VoteMemberNotFoundException extends VoteException {
    public VoteMemberNotFoundException() {
        super(VOTE_MEMBER_NOT_FOUND_ERROR.getErrorCode(),
                VOTE_MEMBER_NOT_FOUND_ERROR.getHttpStatus(),
                VOTE_MEMBER_NOT_FOUND_ERROR.getMessage());
    }
}
