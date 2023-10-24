package com.sidenow.domain.together.childcare.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ChildcareConstants {

    @Getter
    @RequiredArgsConstructor
    public enum ChildcareResponseMessage {
        CREATE_CHILDCARE_POST_SUCCESS("육아해요 게시글이 등록되었습니다."),
        DELETE_CHILDCARE_POST_SUCCESS("육아해요 게시글이 삭제되었습니다."),
        UPDATE_CHILDCARE_POST_SUCCESS("육아해요 게시글이 수정되었습니다"),
        GET_CHILDCARE_POST_SUCCESS("육아해요 게시글을 상세 조회하였습니다."),
        GET_CHILDCARE_POST_LIST_SUCCESS("육아해요 게시글을 전체 조회하였습니다."),
        LIKE_CHILDCARE_SUCCESS("좋아요 처리가 완료되었습니다.");
        private final String message;
    }
}
