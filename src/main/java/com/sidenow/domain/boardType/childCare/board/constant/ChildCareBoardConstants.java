package com.sidenow.domain.boardType.childCare.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ChildCareBoardConstants {

    @Getter
    @RequiredArgsConstructor
    public enum ChildCareBoardResponseMessage {
        REGISTER_CHILDCARE_BOARD_POST_SUCCESS("육아게시판 게시글이 등록되었습니다."),
        DELETE_CHILDCARE_BOARD_POST_SUCCESS("육아게시판 게시글이 삭제되었습니다."),
        UPDATE_CHILDCARE_BOARD_POST_SUCCESS("육아게시판 게시글이 수정되었습니다"),
        GET_CHILDCARE_BOARD_POST_SUCCESS("육아게시판 게시글을 상세 조회하였습니다."),
        GET_CHILDCARE_BOARD_POST_LIST_SUCCESS("육아게시판 게시글을 전체 조회하였습니다.");
        private final String message;
    }
}