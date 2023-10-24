package com.sidenow.domain.together.coBuying.comment.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CoBuyingCommentConstants {

    @Getter
    @RequiredArgsConstructor
    public enum CoBuyingCommentSuccessMessage {
        READ_CO_BUYING_COMMENT_SUCCESS("공구해요 댓글 조회에 성공했습니다"),
        CREATE_CO_BUYING_COMMENT_SUCCESS("공구해요 댓글 생성에 성공했습니다"),
        DELETE_CO_BUYING_COMMENT_SUCCESS("공구해요 댓글 삭제에 성공했습니다"),
        MODIFY_CO_BUYING_COMMENT_SUCCESS("공구해요 댓글 수정에 성공했습니다"),
        LIKE_CO_BUYING_COMMENT_SUCCESS("좋아요 처리가 완료되었습니다.");;
        private final String message;
    }

}
