package com.sidenow.domain.boardType.coBuying.comment.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public abstract class CoBuyingCommentRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "공구해요 게시글 댓글 생성 요청 객체")
    public static class CoBuyingCommentCreateRequest {

        @Schema(description = "부모 댓글 id(대댓글 작성 시 기입)", example = "null")
        private Long parentId;

        @NotBlank(message = "공구해요 게시글 댓글 내용 입력")
        @Schema(description = "댓글 내용", example = "Test Comment")
        private String content;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "공구해요 게시글 댓글 수정 요청 객체")
    public static class CoBuyingCommentUpdateRequest {

        @Schema(description = "부모 댓글 id(대댓글 작성 시 기입)", example = "null")
        private Long parentId;

        @NotBlank(message = "공구해요 게시글 댓글 내용 수정")
        @Schema(description = "댓글 내용", example = "Test Updated Comment")
        private String content;
    }
}
