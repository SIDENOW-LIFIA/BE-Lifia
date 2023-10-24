package com.sidenow.domain.boardType.coBuying.board.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CoBuyingExceptionList {
    CO_BUYING_ID_NOT_FOUND_ERROR("CB0001", HttpStatus.NOT_FOUND, "해당 postId인 게시글이 존재하지 않습니다."),
    CO_BUYING_MEMBER_NOT_FOUND_ERROR("CB0002", HttpStatus.NOT_FOUND, "해당 유저의 게시글이 존재하지 않습니다."),
    CO_BUYING_LIKE_HISTORY_NOT_FOUND_ERROR("CB0002", HttpStatus.NOT_FOUND, "해당 유저의 게시글 좋아요 이력이 존재하지 않습니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
