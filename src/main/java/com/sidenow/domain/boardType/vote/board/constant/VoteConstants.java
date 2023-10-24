package com.sidenow.domain.boardType.vote.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class VoteConstants {

    @Getter
    @RequiredArgsConstructor
    public enum VoteResponseMessage {
        CREATE_VOTE_POST_SUCCESS("투표해요 게시글이 등록되었습니다."),
        DELETE_VOTE_POST_SUCCESS("투표해요 게시글이 삭제되었습니다."),
        UPDATE_VOTE_POST_SUCCESS("투표해요 게시글이 수정되었습니다"),
        GET_VOTE_POST_SUCCESS("투표해요 게시글을 상세 조회하였습니다."),
        GET_VOTE_POST_LIST_SUCCESS("투표해요 게시글을 전체 조회하였습니다."),
        LIKE_VOTE_SUCCESS("좋아요 처리가 완료되었습니다.");
        private final String message;
    }
}
