package com.sidenow.domain.boardType.caution.comment.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CautionCommentConstants {

    @Getter
    @RequiredArgsConstructor
    public enum CautionCommentSuccessMessage {
        READ_CAUTION_COMMENT_SUCCESS("조심해요 댓글 조회에 성공했습니다"),
        CREATE_CAUTION_COMMENT_SUCCESS("조심해요 댓글 생성에 성공했습니다"),
        DELETE_CAUTION_COMMENT_SUCCESS("조심해요 댓글 삭제에 성공했습니다"),
        MODIFY_CAUTION_COMMENT_SUCCESS("조심해요 댓글 수정에 성공했습니다"),
        LIKE_CAUTION_COMMENT_SUCCESS("좋아요 처리가 완료되었습니다.");;
        private final String message;
    }

}
