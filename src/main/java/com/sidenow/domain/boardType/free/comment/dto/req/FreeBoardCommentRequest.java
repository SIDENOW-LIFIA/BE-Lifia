package com.sidenow.domain.boardType.free.comment.dto.req;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.comment.entity.FreeBoardComment;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public abstract class FreeBoardCommentRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "자유게시판 게시글 댓글 생성 요청 객체")
    public static class FreeBoardCommentCreateRequest {

        @NotBlank(message = "자유게시판 게시글 댓글 내용 입력")
        @Schema(description = "댓글 내용", example = "Test Comment")
        private String content;

        public static FreeBoardComment to(FreeBoardCommentCreateRequest req, Member member, FreeBoard freeBoard){

            return FreeBoardComment.builder()
                    .content(req.content)
                    .member(member)
                    .freeBoard(freeBoard)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "자유게시판 게시글 댓글 수정 요청 객체")
    public static class FreeBoardCommentUpdateRequest {

        @NotBlank(message = "자유게시판 게시글 댓글 내용 수정")
        @Schema(description = "댓글 내용", example = "Test Updated Comment")
        private String content;
    }
}
