package com.sidenow.domain.boardType.free.comment.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public abstract class FreeBoardCommentRequest {
    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "자유게시판 게시글의 댓글 생성 요청 객체")
    public static class CreateFreeBoardCommentRequest {
        private final String content;
        private final Long parentId;
    }
}
