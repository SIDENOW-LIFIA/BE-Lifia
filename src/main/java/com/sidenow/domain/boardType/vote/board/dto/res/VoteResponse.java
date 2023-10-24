package com.sidenow.domain.boardType.vote.board.dto.res;

import com.sidenow.domain.boardType.vote.board.entity.Vote;
import com.sidenow.domain.boardType.vote.comment.entity.VoteComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public abstract class VoteResponse {

    @Data
    @AllArgsConstructor
    @Schema(description = "투표해요 생성 응답 객체")
    public static class VoteCreateResponse {
        private Long id;
        private String title;
        private String content;
        private String image;

        public static VoteCreateResponse from(Vote vote) {
            return new VoteCreateResponse(vote.getVoteId(), vote.getTitle(), vote.getContent(), vote.getImage());
        }
    }

    @Data
    @AllArgsConstructor
    @Schema(description = "투표해요 수정 응답 객체")
    public static class VoteUpdateResponse {
        private Long id;
        private String title;
        private String content;

        public static VoteUpdateResponse from(Vote vote) {
            return new VoteUpdateResponse(vote.getVoteId(), vote.getTitle(), vote.getContent());
        }
    }
    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "단일 게시글 조회 응답 객체")
    public static class VoteGetResponse {
        private final Long id;
        private final String title;
        private final String content;
        private final String image;
        private final String nickname;
        private final LocalDateTime createAt;
        private final int hits;
        private final int likes;
        private final int commentsCount;
        private final List<VoteComment> comments;

        public static VoteGetResponse from(Vote vote) {
            return VoteGetResponse.builder()
                    .id(vote.getVoteId())
                    .title(vote.getTitle())
                    .content(vote.getContent())
                    .image(vote.getImage())
                    .nickname(vote.getMember().getNickname())
                    .createAt(vote.getCreatedAt())
                    .hits(vote.getHits())
                    .likes(vote.getLikes())
                    .commentsCount(vote.getVoteComments().size())
                    .comments(vote.getVoteComments())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "게시글 전체 조회 응답 객체")
    public static class VoteGetListResponse {
        @Schema(description = "투표해요 게시글 ID")
        private final Long votePostId;

        @Schema(description = "투표해요 게시글 작성자 닉네임")
        private final String nickname;

        @Schema(description = "투표해요 게시글 제목")
        private final String title;

        @Schema(description = "투표해요 게시글 조회수")
        private final int hits;

        @Schema(description = "투표해요 게시글 좋아요 수")
        private final int likes;

        @Schema(description = "투표해요 게시글 댓글 수")
        private final int commentsCount;

        @Schema(description = "투표해요 게시글 생성 일시")
        private final LocalDateTime createdAt;

        public static VoteGetListResponse from(Vote vote) {
            return VoteGetListResponse.builder()
                    .votePostId(vote.getVoteId())
                    .nickname(vote.getMember().getNickname())
                    .title(vote.getTitle())
                    .hits(vote.getHits())
                    .likes(vote.getLikes())
                    .commentsCount(vote.getVoteComments().size())
                    .createdAt(vote.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "투표해요 게시글 전체 조회")
    public static class AllVotes {
        private final List<VoteGetListResponse> votes;
    }

}
