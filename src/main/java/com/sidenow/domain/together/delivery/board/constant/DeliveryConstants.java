package com.sidenow.domain.together.delivery.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class DeliveryConstants {

    @Getter
    @RequiredArgsConstructor
    public enum DeliveryResponseMessage {
        CREATE_DELIVERY_POST_SUCCESS("배달해요 게시글이 등록되었습니다."),
        DELETE_DELIVERY_POST_SUCCESS("배달해요 게시글이 삭제되었습니다."),
        UPDATE_DELIVERY_POST_SUCCESS("배달해요 게시글이 수정되었습니다"),
        GET_DELIVERY_POST_SUCCESS("배달해요 게시글을 상세 조회하였습니다."),
        GET_DELIVERY_POST_LIST_SUCCESS("배달해요 게시글을 전체 조회하였습니다."),
        LIKE_DELIVERY_SUCCESS("좋아요 처리가 완료되었습니다.");
        private final String message;
    }
}
