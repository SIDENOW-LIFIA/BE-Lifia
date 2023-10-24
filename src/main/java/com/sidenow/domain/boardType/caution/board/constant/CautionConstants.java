package com.sidenow.domain.boardType.caution.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CautionConstants {

    @Getter
    @RequiredArgsConstructor
    public enum CautionResponseMessage {
        CREATE_CAUTION_POST_SUCCESS("조심해요 게시글이 등록되었습니다."),
        DELETE_CAUTION_POST_SUCCESS("조심해요 게시글이 삭제되었습니다."),
        UPDATE_CAUTION_POST_SUCCESS("조심해요 게시글이 수정되었습니다"),
        GET_CAUTION_POST_SUCCESS("조심해요 게시글을 상세 조회하였습니다."),
        GET_CAUTION_POST_LIST_SUCCESS("조심해요 게시글을 전체 조회하였습니다."),
        LIKE_CAUTION_SUCCESS("좋아요 처리가 완료되었습니다.");
        private final String message;
    }
}
