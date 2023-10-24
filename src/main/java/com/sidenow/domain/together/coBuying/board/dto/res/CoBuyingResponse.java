package com.sidenow.domain.together.coBuying.board.dto.res;

import com.sidenow.domain.together.coBuying.board.entity.CoBuying;
import com.sidenow.domain.together.coBuying.comment.entity.CoBuyingComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public abstract class CoBuyingResponse {

    @Data
    @AllArgsConstructor
    @Schema(description = "공구해요 생성 응답 객체")
    public static class CoBuyingCreateResponse {
        private Long id;
        private String title;
        private String content;
        private String image;

        public static CoBuyingCreateResponse from(CoBuying coBuying) {
            return new CoBuyingCreateResponse(coBuying.getCoBuyingId(), coBuying.getTitle(), coBuying.getContent(), coBuying.getImage());
        }
    }

    @Data
    @AllArgsConstructor
    @Schema(description = "공구해요 수정 응답 객체")
    public static class CoBuyingUpdateResponse {
        private Long id;
        private String title;
        private String content;

        public static CoBuyingUpdateResponse from(CoBuying coBuying) {
            return new CoBuyingUpdateResponse(coBuying.getCoBuyingId(), coBuying.getTitle(), coBuying.getContent());
        }
    }
    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "단일 게시글 조회 응답 객체")
    public static class CoBuyingGetResponse {
        private final Long id;
        private final String title;
        private final String content;
        private final String image;
        private final String nickname;
        private final LocalDateTime createAt;
        private final int hits;
        private final int likes;
        private final int commentsCount;
        private final List<CoBuyingComment> comments;

        public static CoBuyingGetResponse from(CoBuying coBuying) {
            return CoBuyingGetResponse.builder()
                    .id(coBuying.getCoBuyingId())
                    .title(coBuying.getTitle())
                    .content(coBuying.getContent())
                    .image(coBuying.getImage())
                    .nickname(coBuying.getMember().getNickname())
                    .createAt(coBuying.getCreatedAt())
                    .hits(coBuying.getHits())
                    .likes(coBuying.getLikes())
                    .commentsCount(coBuying.getCoBuyingComments().size())
                    .comments(coBuying.getCoBuyingComments())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "게시글 전체 조회 응답 객체")
    public static class CoBuyingGetListResponse {
        @Schema(description = "공구해요 게시글 ID")
        private final Long coBuyingPostId;

        @Schema(description = "공구해요 게시글 작성자 닉네임")
        private final String nickname;

        @Schema(description = "공구해요 게시글 제목")
        private final String title;

        @Schema(description = "공구해요 게시글 조회수")
        private final int hits;

        @Schema(description = "공구해요 게시글 좋아요 수")
        private final int likes;

        @Schema(description = "공구해요 게시글 댓글 수")
        private final int commentsCount;

        @Schema(description = "공구해요 게시글 생성 일시")
        private final LocalDateTime createdAt;

        public static CoBuyingGetListResponse from(CoBuying coBuying) {
            return CoBuyingGetListResponse.builder()
                    .coBuyingPostId(coBuying.getCoBuyingId())
                    .nickname(coBuying.getMember().getNickname())
                    .title(coBuying.getTitle())
                    .hits(coBuying.getHits())
                    .likes(coBuying.getLikes())
                    .commentsCount(coBuying.getCoBuyingComments().size())
                    .createdAt(coBuying.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "공구해요 게시글 전체 조회")
    public static class AllCoBuyings {
        private final List<CoBuyingGetListResponse> coBuyings;
    }

}
