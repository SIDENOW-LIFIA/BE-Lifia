package com.sidenow.domain.boardType.free.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class FreeBoardConstants {

    @Getter
    @RequiredArgsConstructor
    public enum FreeBoardResponseMessage {
        CREATE_FREE_BOARD_POST_SUCCESS("자유게시판 게시글이 등록되었습니다."),
        DELETE_FREE_BOARD_POST_SUCCESS("자유게시판 게시글이 삭제되었습니다."),
        MODIFY_FREE_BOARD_POST_SUCCESS("자유게시판 게시글이 수정되었습니다"),
        READ_FREE_BOARD_POST_DETAIL_SUCCESS("자유게시판 게시글을 상세 조회하였습니다."),
        READ_FREE_BOARD_POST_ALL_SUCCESS("자유게시판 게시글을 전체 조회하였습니다.");
        private final String message;
    }
}
