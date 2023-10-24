package com.sidenow.domain.boardType.delivery.board.dto.res;

import com.sidenow.domain.boardType.delivery.board.entity.Delivery;
import com.sidenow.domain.boardType.delivery.comment.entity.DeliveryComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public abstract class DeliveryResponse {

    @Data
    @AllArgsConstructor
    @Schema(description = "배달해요 생성 응답 객체")
    public static class DeliveryCreateResponse {
        private Long id;
        private String title;
        private String content;
        private String image;

        public static DeliveryCreateResponse from(Delivery delivery) {
            return new DeliveryCreateResponse(delivery.getDeliveryId(), delivery.getTitle(), delivery.getContent(), delivery.getImage());
        }
    }

    @Data
    @AllArgsConstructor
    @Schema(description = "배달해요 수정 응답 객체")
    public static class DeliveryUpdateResponse {
        private Long id;
        private String title;
        private String content;

        public static DeliveryUpdateResponse from(Delivery delivery) {
            return new DeliveryUpdateResponse(delivery.getDeliveryId(), delivery.getTitle(), delivery.getContent());
        }
    }
    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "단일 게시글 조회 응답 객체")
    public static class DeliveryGetResponse {
        private final Long id;
        private final String title;
        private final String content;
        private final String image;
        private final String nickname;
        private final LocalDateTime createAt;
        private final int hits;
        private final int likes;
        private final int commentsCount;
        private final List<DeliveryComment> comments;

        public static DeliveryGetResponse from(Delivery delivery) {
            return DeliveryGetResponse.builder()
                    .id(delivery.getDeliveryId())
                    .title(delivery.getTitle())
                    .content(delivery.getContent())
                    .image(delivery.getImage())
                    .nickname(delivery.getMember().getNickname())
                    .createAt(delivery.getCreatedAt())
                    .hits(delivery.getHits())
                    .likes(delivery.getLikes())
                    .commentsCount(delivery.getDeliveryComments().size())
                    .comments(delivery.getDeliveryComments())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "게시글 전체 조회 응답 객체")
    public static class DeliveryGetListResponse {
        @Schema(description = "배달해요 게시글 ID")
        private final Long deliveryPostId;

        @Schema(description = "배달해요 게시글 작성자 닉네임")
        private final String nickname;

        @Schema(description = "배달해요 게시글 제목")
        private final String title;

        @Schema(description = "배달해요 게시글 조회수")
        private final int hits;

        @Schema(description = "배달해요 게시글 좋아요 수")
        private final int likes;

        @Schema(description = "배달해요 게시글 댓글 수")
        private final int commentsCount;

        @Schema(description = "배달해요 게시글 생성 일시")
        private final LocalDateTime createdAt;

        public static DeliveryGetListResponse from(Delivery delivery) {
            return DeliveryGetListResponse.builder()
                    .deliveryPostId(delivery.getDeliveryId())
                    .nickname(delivery.getMember().getNickname())
                    .title(delivery.getTitle())
                    .hits(delivery.getHits())
                    .likes(delivery.getLikes())
                    .commentsCount(delivery.getDeliveryComments().size())
                    .createdAt(delivery.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "배달해요 게시글 전체 조회")
    public static class AllDeliverys {
        private final List<DeliveryGetListResponse> deliverys;
    }

}
