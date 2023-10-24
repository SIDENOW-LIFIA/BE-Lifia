package com.sidenow.domain.boardType.coBuying.comment.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CoBuyingCommentExceptionList {
    CO_BUYING_COMMENT_ID_NOT_FOUND_ERROR("CC0001", HttpStatus.NOT_FOUND, "해당 coBuyingCommentId인 공구해요 게시글의 댓글이 존재하지 않습니다."),
    CO_BUYING_COMMENT_AUTH_ERROR("CC0002", HttpStatus.UNAUTHORIZED, "해당 댓글의 작성자가 아닙니다."),
    CO_BUYING_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR("CC0003", HttpStatus.NOT_FOUND, "해당 유저의 댓글 좋아요 이력이 존재하지 않습니다.");
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
