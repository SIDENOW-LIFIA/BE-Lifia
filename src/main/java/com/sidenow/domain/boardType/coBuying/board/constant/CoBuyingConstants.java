package com.sidenow.domain.boardType.cobuying.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CobuyingConstants {

    @Getter
    @RequiredArgsConstructor
    public enum CobuyingResponseMessage {
        CREATE_COBUYING_POST_SUCCESS("공구해요 게시글이 등록되었습니다."),
        DELETE_COBUYING_POST_SUCCESS("공구해요 게시글이 삭제되었습니다."),
        UPDATE_COBUYING_POST_SUCCESS("공구해요 게시글이 수정되었습니다"),
        GET_COBUYING_POST_SUCCESS("공구해요 게시글을 상세 조회하였습니다."),
        GET_COBUYING_POST_LIST_SUCCESS("공구해요 게시글을 전체 조회하였습니다."),
        LIKE_COBUYING_SUCCESS("좋아요 처리가 완료되었습니다.");
        private final String message;
    }
}
