package com.sidenow.domain.boardType.free.board.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

public abstract class FreeBoardResponse {

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "게시글 등록 응답 객체")
    public static class CreatePostResponse {
        private Long postId;
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "단일 게시글 조회 응답 객체")
    public static class ReadPostDetailResponse {
        private final String title;
        private final String content;
        private final String nickname;
        private final String imageUrl;
        private final int hits;
        private final int likes;
        private final int comments;
        private String createdAt;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @Schema(description = "게시글 전체 조회 응답 객체")
    public static class GetPostAllResponse {
        private String title;
        private String content;
        private LocalDateTime createdAt;
    }

}
