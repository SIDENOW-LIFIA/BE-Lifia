package com.sidenow.domain.together.coBuying.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CoBuyingConstants {

    @Getter
    @RequiredArgsConstructor
    public enum CoBuyingResponseMessage {
        CREATE_CO_BUYING_POST_SUCCESS("공구해요 게시글이 등록되었습니다."),
        DELETE_CO_BUYING_POST_SUCCESS("공구해요 게시글이 삭제되었습니다."),
        UPDATE_CO_BUYING_POST_SUCCESS("공구해요 게시글이 수정되었습니다"),
        GET_CO_BUYING_POST_SUCCESS("공구해요 게시글을 상세 조회하였습니다."),
        GET_CO_BUYING_POST_LIST_SUCCESS("공구해요 게시글을 전체 조회하였습니다."),
        LIKE_CO_BUYING_SUCCESS("좋아요 처리가 완료되었습니다.");
        private final String message;
    }
}
