package com.sidenow.domain.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class BoardConstant {
    @Getter
    @RequiredArgsConstructor
    public enum EBoardResponseMessage{
        CREATE_POST_SUCCESS("게시글이 등록되었습니다.");
        private final String message;
    }
}
