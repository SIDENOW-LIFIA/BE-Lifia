package com.sidenow.domain.boardType.vote.board.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum VoteExceptionList {
    VOTE_ID_NOT_FOUND_ERROR("C0001", HttpStatus.NOT_FOUND, "해당 postId인 게시글이 존재하지 않습니다."),
    VOTE_MEMBER_NOT_FOUND_ERROR("C0002", HttpStatus.NOT_FOUND, "해당 유저의 게시글이 존재하지 않습니다."),
    VOTE_LIKE_HISTORY_NOT_FOUND_ERROR("C0002", HttpStatus.NOT_FOUND, "해당 유저의 게시글 좋아요 이력이 존재하지 않습니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
