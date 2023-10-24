package com.sidenow.domain.together.childcare.comment.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChildcareCommentExceptionList {
    CHILDCARE_COMMENT_ID_NOT_FOUND_ERROR("CCC0001", HttpStatus.NOT_FOUND, "해당 childcareCommentId인 육아해요 게시글의 댓글이 존재하지 않습니다."),
    CHILDCARE_COMMENT_AUTH_ERROR("CCC0002", HttpStatus.UNAUTHORIZED, "해당 댓글의 작성자가 아닙니다."),
    CHILDCARE_COMMENT_LIKE_HISTORY_NOT_FOUND_ERROR("CCC0003", HttpStatus.NOT_FOUND, "해당 유저의 댓글 좋아요 이력이 존재하지 않습니다.");
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
