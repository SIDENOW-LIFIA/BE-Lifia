package com.sidenow.domain.boardType.coBuying.comment.dto.res;

import com.sidenow.domain.boardType.coBuying.comment.entity.CoBuyingComment;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

public abstract class CoBuyingCommentResponse {

    @Data
    @AllArgsConstructor
    @Schema(description = "공구해요 게시글 댓글 생성 응답 객체")
    public static class CoBuyingCommentCreateResponse {
        private Long id;
        private Member writer;
        private String content;

        public static CoBuyingCommentCreateResponse from(CoBuyingComment coBuyingComment) {
            return new CoBuyingCommentCreateResponse(coBuyingComment.getCommentId(), coBuyingComment.getMember(), coBuyingComment.getContent());
        }
    }

    @Data
    @AllArgsConstructor
    @Schema(description = "공구해요 게시글 댓글 수정 응답 객체")
    public static class CoBuyingCommentUpdateResponse {
        private Long id;
        private Member writer;
        private String content;

        public static CoBuyingCommentUpdateResponse from(CoBuyingComment coBuyingComment) {
            return new CoBuyingCommentUpdateResponse(coBuyingComment.getCommentId(), coBuyingComment.getMember(), coBuyingComment.getContent());
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "공구해요 게시글의 댓글 전체 조회 응답 객체")
    public static class CoBuyingGetCommentListResponse {
        private final Long id;
        private final Long parentId;
        private final String nickname; // 작성자 별명
        private final String content; // 내용
        private final LocalDateTime createdAt; // 생성일시

        public static CoBuyingGetCommentListResponse from(CoBuyingComment coBuyingComments) {
            Member member = coBuyingComments.getMember();

            return CoBuyingGetCommentListResponse.builder()
                    .id(coBuyingComments.getCommentId())
                    .nickname(member.getNickname())
                    .content(coBuyingComments.getContent())
                    .createdAt(coBuyingComments.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "공구해요 게시글의 대댓글(자식) 상세 조회 응답 객체")
    public static class ReadCoBuyingChildCommentResponse {
        private final String nickname;
        private final String content;
        private final boolean isDeleted;
        private final LocalDateTime createdAt;
        private final Long parentId;

        public static ReadCoBuyingChildCommentResponse from(CoBuyingComment coBuyingComments) {
            Member member = coBuyingComments.getMember();
            return ReadCoBuyingChildCommentResponse.builder()
                    .nickname(member.getNickname())
                    .content(coBuyingComments.getContent())
                    .createdAt(coBuyingComments.getCreatedAt())
                    .isDeleted(coBuyingComments.getIsDeleted())
                    .build();
        }
    }
}
