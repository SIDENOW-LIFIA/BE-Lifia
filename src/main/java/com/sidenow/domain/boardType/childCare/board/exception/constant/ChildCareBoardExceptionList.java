package com.sidenow.domain.boardType.childCare.board.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChildCareBoardExceptionList {
    NOT_FOUND_CHILDCARE_BOARD_POST_ID_ERROR("FB0001", HttpStatus.NOT_FOUND, "해당 postId인 육아게시판 게시글이 존재하지 않습니다."),
    NOT_FOUND_CHILDCARE_BOARD_POST_BY_MEMBER_ERROR("FB0002", HttpStatus.NOT_FOUND, "해당 유저의 육아게시판 게시글이 존재하지 않습니다.");
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
