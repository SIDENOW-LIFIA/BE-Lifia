package com.sidenow.domain.freeboard.comment.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class FreeBoardCommentConstants {

    @Getter
    @RequiredArgsConstructor
    public enum FreeBoardCommentSuccessMessage {
        READ_FREE_BOARD_COMMENT_SUCCESS("자유게시판 댓글 조회에 성공했습니다"),
        CREATE_FREE_BOARD_COMMENT_SUCCESS("자유게시판 댓글 생성에 성공했습니다"),
        DELETE_FREE_BOARD_COMMENT_SUCCESS("자유게시판 댓글 삭제에 성공했습니다"),
        MODIFY_FREE_BOARD_COMMENT_SUCCESS("자유게시판 댓글 수정에 성공했습니다"),
        LIKE_FREE_BOARD_COMMENT_SUCCESS("좋아요 처리가 완료되었습니다.");;
        private final String message;
    }

}
