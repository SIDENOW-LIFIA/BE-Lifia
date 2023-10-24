package com.sidenow.domain.boardType.delivery.comment.dto.res;

import com.sidenow.domain.boardType.delivery.comment.entity.DeliveryComment;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

public abstract class DeliveryCommentResponse {

    @Data
    @AllArgsConstructor
    @Schema(description = "배달해요 게시글 댓글 생성 응답 객체")
    public static class DeliveryCommentCreateResponse {
        private Long id;
        private Member writer;
        private String content;

        public static DeliveryCommentCreateResponse from(DeliveryComment deliveryComment) {
            return new DeliveryCommentCreateResponse(deliveryComment.getCommentId(), deliveryComment.getMember(), deliveryComment.getContent());
        }
    }

    @Data
    @AllArgsConstructor
    @Schema(description = "배달해요 게시글 댓글 수정 응답 객체")
    public static class DeliveryCommentUpdateResponse {
        private Long id;
        private Member writer;
        private String content;

        public static DeliveryCommentUpdateResponse from(DeliveryComment deliveryComment) {
            return new DeliveryCommentUpdateResponse(deliveryComment.getCommentId(), deliveryComment.getMember(), deliveryComment.getContent());
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "배달해요 게시글의 댓글 전체 조회 응답 객체")
    public static class DeliveryGetCommentListResponse {
        private final Long id;
        private final Long parentId;
        private final String nickname; // 작성자 별명
        private final String content; // 내용
        private final LocalDateTime createdAt; // 생성일시

        public static DeliveryGetCommentListResponse from(DeliveryComment deliveryComments) {
            Member member = deliveryComments.getMember();

            return DeliveryGetCommentListResponse.builder()
                    .id(deliveryComments.getCommentId())
                    .nickname(member.getNickname())
                    .content(deliveryComments.getContent())
                    .createdAt(deliveryComments.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "배달해요 게시글의 대댓글(자식) 상세 조회 응답 객체")
    public static class ReadDeliveryChildCommentResponse {
        private final String nickname;
        private final String content;
        private final boolean isDeleted;
        private final LocalDateTime createdAt;
        private final Long parentId;

        public static ReadDeliveryChildCommentResponse from(DeliveryComment deliveryComments) {
            Member member = deliveryComments.getMember();
            return ReadDeliveryChildCommentResponse.builder()
                    .nickname(member.getNickname())
                    .content(deliveryComments.getContent())
                    .createdAt(deliveryComments.getCreatedAt())
                    .isDeleted(deliveryComments.getIsDeleted())
                    .build();
        }
    }
}
