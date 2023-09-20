package com.sidenow.domain.boardType.childCare.comment.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ChildCareBoardCommentConstants {

    @Getter
    @RequiredArgsConstructor
    public enum ChildCareBoardCommentSuccessMessage {
        READ_CHILDCARE_BOARD_COMMENT_SUCCESS("육아게시판 댓글 조회에 성공했습니다"),
        CREATE_CHILDCARE_BOARD_COMMENT_SUCCESS("육아게시판 댓글 생성에 성공했습니다"),
        DELETE_CHILDCARE_BOARD_COMMENT_SUCCESS("육아게시판 댓글 삭제에 성공했습니다"),
        MODIFY_CHILDCARE_BOARD_COMMENT_SUCCESS("육아게시판 댓글 수정에 성공했습니다");
        private final String message;
    }

}
