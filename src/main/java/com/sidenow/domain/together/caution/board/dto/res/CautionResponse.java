package com.sidenow.domain.together.caution.board.dto.res;

import com.sidenow.domain.together.caution.board.entity.Caution;
import com.sidenow.domain.together.caution.comment.entity.CautionComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public abstract class CautionResponse {

    @Data
    @AllArgsConstructor
    @Schema(description = "조심해요 생성 응답 객체")
    public static class CautionCreateResponse {
        private Long id;
        private String title;
        private String content;
        private String image;

        public static CautionCreateResponse from(Caution caution) {
            return new CautionCreateResponse(caution.getCautionId(), caution.getTitle(), caution.getContent(), caution.getImage());
        }
    }

    @Data
    @AllArgsConstructor
    @Schema(description = "조심해요 수정 응답 객체")
    public static class CautionUpdateResponse {
        private Long id;
        private String title;
        private String content;

        public static CautionUpdateResponse from(Caution caution) {
            return new CautionUpdateResponse(caution.getCautionId(), caution.getTitle(), caution.getContent());
        }
    }
    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "단일 게시글 조회 응답 객체")
    public static class CautionGetResponse {
        private final Long id;
        private final String title;
        private final String content;
        private final String image;
        private final String nickname;
        private final LocalDateTime createAt;
        private final int hits;
        private final int likes;
        private final int commentsCount;
        private final List<CautionComment> comments;

        public static CautionGetResponse from(Caution caution) {
            return CautionGetResponse.builder()
                    .id(caution.getCautionId())
                    .title(caution.getTitle())
                    .content(caution.getContent())
                    .image(caution.getImage())
                    .nickname(caution.getMember().getNickname())
                    .createAt(caution.getCreatedAt())
                    .hits(caution.getHits())
                    .likes(caution.getLikes())
                    .commentsCount(caution.getCautionComments().size())
                    .comments(caution.getCautionComments())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "게시글 전체 조회 응답 객체")
    public static class CautionGetListResponse {
        @Schema(description = "조심해요 게시글 ID")
        private final Long cautionPostId;

        @Schema(description = "조심해요 게시글 작성자 닉네임")
        private final String nickname;

        @Schema(description = "조심해요 게시글 제목")
        private final String title;

        @Schema(description = "조심해요 게시글 조회수")
        private final int hits;

        @Schema(description = "조심해요 게시글 좋아요 수")
        private final int likes;

        @Schema(description = "조심해요 게시글 댓글 수")
        private final int commentsCount;

        @Schema(description = "조심해요 게시글 생성 일시")
        private final LocalDateTime createdAt;

        public static CautionGetListResponse from(Caution caution) {
            return CautionGetListResponse.builder()
                    .cautionPostId(caution.getCautionId())
                    .nickname(caution.getMember().getNickname())
                    .title(caution.getTitle())
                    .hits(caution.getHits())
                    .likes(caution.getLikes())
                    .commentsCount(caution.getCautionComments().size())
                    .createdAt(caution.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "조심해요 게시글 전체 조회")
    public static class AllCautions {
        private final List<CautionGetListResponse> cautions;
    }

}
