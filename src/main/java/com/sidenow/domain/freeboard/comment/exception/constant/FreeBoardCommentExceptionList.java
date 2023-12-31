package com.sidenow.domain.freeboard.comment.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FreeBoardCommentExceptionList {
    FREE_BOARD_COMMENT_ID_NOT_FOUND_ERROR("FBC0001", HttpStatus.NOT_FOUND, "해당 freeBoardCommentId인 자유게시판 게시글의 댓글이 존재하지 않습니다."),
    FREE_BOARD_COMMENT_AUTH_ERROR("FBC0002", HttpStatus.UNAUTHORIZED, "해당 댓글의 작성자가 아닙니다."),
    FREE_BOARD_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR("FBC0003", HttpStatus.NOT_FOUND, "해당 유저의 댓글 좋아요 이력이 존재하지 않습니다.");
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
