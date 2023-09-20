package com.sidenow.domain.boardType.childCare.comment.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChildCareBoardCommentExceptionList {
    NOT_FOUND_CHILDCARE_BOARD_COMMENT_ID_ERROR("FBC0001", HttpStatus.NOT_FOUND, "해당 childCareBoardCommentId인 육아게시판 게시글의 댓글이 존재하지 않습니다."),
    CHILDCARE_BOARD_COMMENT_AUTH_ERROR("FBC0002", HttpStatus.UNAUTHORIZED, "해당 댓글의 작성자가 아닙니다.");
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
