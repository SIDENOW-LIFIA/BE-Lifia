package com.sidenow.domain.together.caution.board.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CautionExceptionList {
    CAUTION_ID_NOT_FOUND_ERROR("C0001", HttpStatus.NOT_FOUND, "해당 postId인 게시글이 존재하지 않습니다."),
    CAUTION_MEMBER_NOT_FOUND_ERROR("C0002", HttpStatus.NOT_FOUND, "해당 유저의 게시글이 존재하지 않습니다."),
    CAUTION_LIKE_HISTORY_NOT_FOUND_ERROR("C0002", HttpStatus.NOT_FOUND, "해당 유저의 게시글 좋아요 이력이 존재하지 않습니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
