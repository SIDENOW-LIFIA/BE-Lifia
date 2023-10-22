package com.sidenow.domain.boardType.free.comment.dto.res;

import com.sidenow.domain.boardType.free.comment.entity.FreeBoardComment;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

public abstract class FreeBoardCommentResponse {

    @Data
    @AllArgsConstructor
    @Schema(description = "자유게시판 게시글 댓글 생성 응답 객체")
    public static class FreeBoardCommentCreateResponse {
        private Long id;
        private Member writer;
        private String content;

        public static FreeBoardCommentCreateResponse from(FreeBoardComment freeBoardComment) {
            return new FreeBoardCommentCreateResponse(freeBoardComment.getCommentId(), freeBoardComment.getMember(), freeBoardComment.getContent());
        }
    }

    @Data
    @AllArgsConstructor
    @Schema(description = "자유게시판 게시글 댓글 수정 응답 객체")
    public static class FreeBoardCommentUpdateResponse {
        private Long id;
        private Member writer;
        private String content;

        public static FreeBoardCommentUpdateResponse from(FreeBoardComment freeBoardComment) {
            return new FreeBoardCommentUpdateResponse(freeBoardComment.getCommentId(), freeBoardComment.getMember(), freeBoardComment.getContent());
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "자유게시판 게시글의 댓글 전체 조회 응답 객체")
    public static class FreeBoardGetCommentListResponse {
        private final Long freeBoardCommentId;
        private final String nickname; // 작성자 별명
        private final String content; // 내용
        private final LocalDateTime createdAt; // 생성일시

        public static FreeBoardGetCommentListResponse from(FreeBoardComment freeBoardComments) {
            Member member = freeBoardComments.getMember();

            return FreeBoardGetCommentListResponse.builder()
                    .freeBoardCommentId(freeBoardComments.getCommentId())
                    .nickname(member.getNickname())
                    .content(freeBoardComments.getContent())
                    .createdAt(freeBoardComments.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "자유게시판 게시글의 대댓글(자식) 상세 조회 응답 객체")
    public static class ReadFreeBoardChildCommentResponse {
        private final String nickname;
        private final String content;
        private final boolean isDeleted;
        private final LocalDateTime createdAt;
        private final Long parentId;

        public static ReadFreeBoardChildCommentResponse from(FreeBoardComment freeBoardComments) {
            Member member = freeBoardComments.getMember();
            return ReadFreeBoardChildCommentResponse.builder()
                    .nickname(member.getNickname())
                    .content(freeBoardComments.getContent())
                    .createdAt(freeBoardComments.getCreatedAt())
                    .isDeleted(freeBoardComments.getIsDeleted())
                    .build();
        }
    }
}
