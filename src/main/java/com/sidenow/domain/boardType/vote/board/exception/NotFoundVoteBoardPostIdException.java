package com.sidenow.domain.boardType.vote.board.exception;

import static com.sidenow.domain.boardType.vote.board.exception.constant.VoteBoardExceptionList.NOT_FOUND_VOTE_BOARD_POST_ID_ERROR;

public class NotFoundVoteBoardPostIdException extends VoteBoardException{
    public NotFoundVoteBoardPostIdException() {
        super(NOT_FOUND_VOTE_BOARD_POST_ID_ERROR.getErrorCode(),
                NOT_FOUND_VOTE_BOARD_POST_ID_ERROR.getHttpStatus(),
                NOT_FOUND_VOTE_BOARD_POST_ID_ERROR.getMessage());
    }
}
