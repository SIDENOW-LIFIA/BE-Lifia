package com.sidenow.domain.freeboard.board.dto.res;

import com.sidenow.domain.freeboard.board.entity.FreeBoard;
import com.sidenow.domain.freeboard.comment.entity.FreeBoardComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public abstract class FreeBoardResponse {

    @Data
    @AllArgsConstructor
    @Schema(description = "자유게시판 생성 응답 객체")
    public static class FreeBoardCreateResponse {
        private Long id;
        private String title;
        private String content;
        private String image;
        private String openLink;

        public static FreeBoardCreateResponse from(FreeBoard freeBoard) {
            return new FreeBoardCreateResponse(freeBoard.getFreeBoardId(), freeBoard.getTitle(),
                    freeBoard.getContent(), freeBoard.getImage(), freeBoard.getOpenLink());
        }
    }

    @Data
    @AllArgsConstructor
    @Schema(description = "자유게시판 수정 응답 객체")
    public static class FreeBoardUpdateResponse {
        private Long id;
        private String title;
        private String content;

        public static FreeBoardUpdateResponse from(FreeBoard freeBoard) {
            return new FreeBoardUpdateResponse(freeBoard.getFreeBoardId(), freeBoard.getTitle(), freeBoard.getContent());
        }
    }
    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "단일 게시글 조회 응답 객체")
    public static class FreeBoardGetResponse {
        private final Long id;
        private final String title;
        private final String content;
        private final String image;
        private final String nickname;
        private final LocalDateTime createAt;
        private final int hits;
        private final int likes;
        private final int commentsCount;
        private final List<FreeBoardComment> comments;

        public static FreeBoardGetResponse from(FreeBoard freeBoard) {
            return FreeBoardGetResponse.builder()
                    .id(freeBoard.getFreeBoardId())
                    .title(freeBoard.getTitle())
                    .content(freeBoard.getContent())
                    .image(freeBoard.getImage())
                    .nickname(freeBoard.getMember().getNickname())
                    .createAt(freeBoard.getCreatedAt())
                    .hits(freeBoard.getHits())
                    .likes(freeBoard.getLikes())
                    .commentsCount(freeBoard.getFreeBoardComments().size())
                    .comments(freeBoard.getFreeBoardComments())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "게시글 전체 조회 응답 객체")
    public static class FreeBoardGetListResponse {
        @Schema(description = "자유게시판 게시글 ID")
        private final Long freeBoardPostId;

        @Schema(description = "자유게시판 게시글 작성자 닉네임")
        private final String nickname;

        @Schema(description = "자유게시판 게시글 제목")
        private final String title;

        @Schema(description = "자유게시판 게시글 조회수")
        private final int hits;

        @Schema(description = "자유게시판 게시글 좋아요 수")
        private final int likes;

        @Schema(description = "자유게시판 게시글 댓글 수")
        private final int commentsCount;

        @Schema(description = "자유게시판 게시글 생성 일시")
        private final LocalDateTime createdAt;

        public static FreeBoardGetListResponse from(FreeBoard freeBoard) {
            return FreeBoardGetListResponse.builder()
                    .freeBoardPostId(freeBoard.getFreeBoardId())
                    .nickname(freeBoard.getMember().getNickname())
                    .title(freeBoard.getTitle())
                    .hits(freeBoard.getHits())
                    .likes(freeBoard.getLikes())
                    .commentsCount(freeBoard.getFreeBoardComments().size())
                    .createdAt(freeBoard.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "자유게시판 게시글 전체 조회")
    public static class AllFreeBoards {
        private final List<FreeBoardGetListResponse> freeBoards;
    }

}
