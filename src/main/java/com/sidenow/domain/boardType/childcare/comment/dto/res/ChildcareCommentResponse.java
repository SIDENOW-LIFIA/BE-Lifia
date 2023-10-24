package com.sidenow.domain.boardType.childcare.comment.dto.res;

import com.sidenow.domain.boardType.childcare.comment.entity.ChildcareComment;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

public abstract class ChildcareCommentResponse {

    @Data
    @AllArgsConstructor
    @Schema(description = "육아해요 게시글 댓글 생성 응답 객체")
    public static class ChildcareCommentCreateResponse {
        private Long id;
        private Member writer;
        private String content;

        public static ChildcareCommentCreateResponse from(ChildcareComment childcareComment) {
            return new ChildcareCommentCreateResponse(childcareComment.getCommentId(), childcareComment.getMember(), childcareComment.getContent());
        }
    }

    @Data
    @AllArgsConstructor
    @Schema(description = "육아해요 게시글 댓글 수정 응답 객체")
    public static class ChildcareCommentUpdateResponse {
        private Long id;
        private Member writer;
        private String content;

        public static ChildcareCommentUpdateResponse from(ChildcareComment childcareComment) {
            return new ChildcareCommentUpdateResponse(childcareComment.getCommentId(), childcareComment.getMember(), childcareComment.getContent());
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "육아해요 게시글의 댓글 전체 조회 응답 객체")
    public static class ChildcareGetCommentListResponse {
        private final Long id;
        private final Long parentId;
        private final String nickname; // 작성자 별명
        private final String content; // 내용
        private final LocalDateTime createdAt; // 생성일시

        public static ChildcareGetCommentListResponse from(ChildcareComment childcareComments) {
            Member member = childcareComments.getMember();

            return ChildcareGetCommentListResponse.builder()
                    .id(childcareComments.getCommentId())
                    .nickname(member.getNickname())
                    .content(childcareComments.getContent())
                    .createdAt(childcareComments.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "육아해요 게시글의 대댓글(자식) 상세 조회 응답 객체")
    public static class ReadChildcareChildCommentResponse {
        private final String nickname;
        private final String content;
        private final boolean isDeleted;
        private final LocalDateTime createdAt;
        private final Long parentId;

        public static ReadChildcareChildCommentResponse from(ChildcareComment childcareComments) {
            Member member = childcareComments.getMember();
            return ReadChildcareChildCommentResponse.builder()
                    .nickname(member.getNickname())
                    .content(childcareComments.getContent())
                    .createdAt(childcareComments.getCreatedAt())
                    .isDeleted(childcareComments.getIsDeleted())
                    .build();
        }
    }
}
