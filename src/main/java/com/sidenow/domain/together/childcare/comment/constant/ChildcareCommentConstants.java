package com.sidenow.domain.together.childcare.comment.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ChildcareCommentConstants {

    @Getter
    @RequiredArgsConstructor
    public enum ChildcareCommentSuccessMessage {
        READ_CHILDCARE_COMMENT_SUCCESS("육아해요 댓글 조회에 성공했습니다"),
        CREATE_CHILDCARE_COMMENT_SUCCESS("육아해요 댓글 생성에 성공했습니다"),
        DELETE_CHILDCARE_COMMENT_SUCCESS("육아해요 댓글 삭제에 성공했습니다"),
        MODIFY_CHILDCARE_COMMENT_SUCCESS("육아해요 댓글 수정에 성공했습니다"),
        LIKE_CHILDCARE_COMMENT_SUCCESS("좋아요 처리가 완료되었습니다.");;
        private final String message;
    }

}
