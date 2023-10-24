package com.sidenow.domain.together.caution.comment.dto.res;

import com.sidenow.domain.together.caution.comment.entity.CautionComment;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

public abstract class CautionCommentResponse {

    @Data
    @AllArgsConstructor
    @Schema(description = "조심해요 게시글 댓글 생성 응답 객체")
    public static class CautionCommentCreateResponse {
        private Long id;
        private Member writer;
        private String content;

        public static CautionCommentCreateResponse from(CautionComment cautionComment) {
            return new CautionCommentCreateResponse(cautionComment.getCommentId(), cautionComment.getMember(), cautionComment.getContent());
        }
    }

    @Data
    @AllArgsConstructor
    @Schema(description = "조심해요 게시글 댓글 수정 응답 객체")
    public static class CautionCommentUpdateResponse {
        private Long id;
        private Member writer;
        private String content;

        public static CautionCommentUpdateResponse from(CautionComment cautionComment) {
            return new CautionCommentUpdateResponse(cautionComment.getCommentId(), cautionComment.getMember(), cautionComment.getContent());
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "조심해요 게시글의 댓글 전체 조회 응답 객체")
    public static class CautionGetCommentListResponse {
        private final Long id;
        private final Long parentId;
        private final String nickname; // 작성자 별명
        private final String content; // 내용
        private final LocalDateTime createdAt; // 생성일시

        public static CautionGetCommentListResponse from(CautionComment cautionComments) {
            Member member = cautionComments.getMember();

            return CautionGetCommentListResponse.builder()
                    .id(cautionComments.getCommentId())
                    .nickname(member.getNickname())
                    .content(cautionComments.getContent())
                    .createdAt(cautionComments.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "조심해요 게시글의 대댓글(자식) 상세 조회 응답 객체")
    public static class ReadCautionChildCommentResponse {
        private final String nickname;
        private final String content;
        private final boolean isDeleted;
        private final LocalDateTime createdAt;
        private final Long parentId;

        public static ReadCautionChildCommentResponse from(CautionComment cautionComments) {
            Member member = cautionComments.getMember();
            return ReadCautionChildCommentResponse.builder()
                    .nickname(member.getNickname())
                    .content(cautionComments.getContent())
                    .createdAt(cautionComments.getCreatedAt())
                    .isDeleted(cautionComments.getIsDeleted())
                    .build();
        }
    }
}
