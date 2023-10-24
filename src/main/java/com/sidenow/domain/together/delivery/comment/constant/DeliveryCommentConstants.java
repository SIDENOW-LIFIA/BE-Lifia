package com.sidenow.domain.together.delivery.comment.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class DeliveryCommentConstants {

    @Getter
    @RequiredArgsConstructor
    public enum DeliveryCommentSuccessMessage {
        READ_DELIVERY_COMMENT_SUCCESS("배달해요 댓글 조회에 성공했습니다"),
        CREATE_DELIVERY_COMMENT_SUCCESS("배달해요 댓글 생성에 성공했습니다"),
        DELETE_DELIVERY_COMMENT_SUCCESS("배달해요 댓글 삭제에 성공했습니다"),
        MODIFY_DELIVERY_COMMENT_SUCCESS("배달해요 댓글 수정에 성공했습니다"),
        LIKE_DELIVERY_COMMENT_SUCCESS("좋아요 처리가 완료되었습니다.");;
        private final String message;
    }

}
