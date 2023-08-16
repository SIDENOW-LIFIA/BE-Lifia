package com.sidenow.domain.boardType.free.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum EFreeBoardResponseMessage {

    public enum EBoardResponseMessage{
        CREATE_POST_SUCCESS("게시글이 등록되었습니다.");
        private final String message;
    }
}
