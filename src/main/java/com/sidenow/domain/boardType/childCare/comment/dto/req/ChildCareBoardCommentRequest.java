package com.sidenow.domain.boardType.childCare.comment.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public abstract class ChildCareBoardCommentRequest {
    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "육아게시판 게시글의 댓글 생성 요청 객체")
    public static class RegisterChildCareBoardCommentRequest {
        @Schema(description = "댓글 작성자 id")
        private final Long memberId;

        @Schema(description = "댓글 내용")
        private final String content;

        @Schema(description = "부모 댓글")
        private final Long parentId;
    }
}
