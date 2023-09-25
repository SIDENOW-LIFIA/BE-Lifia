package com.sidenow.domain.boardType.vote.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class VoteBoardConstants {

    @Getter
    @RequiredArgsConstructor
    public enum VoteBoardResponseMessage {
        REGISTER_VOTE_BOARD_POST_SUCCESS("투표게시판 게시글이 등록되었습니다."),
        DELETE_VOTE_BOARD_POST_SUCCESS("투표게시판 게시글이 삭제되었습니다."),
        UPDATE_VOTE_BOARD_POST_SUCCESS("투표게시판 게시글이 수정되었습니다"),
        GET_VOTE_BOARD_POST_SUCCESS("투표게시판 게시글을 상세 조회하였습니다."),
        GET_VOTE_BOARD_POST_LIST_SUCCESS("투표게시판 게시글을 전체 조회하였습니다.");
        private final String message;
    }
}