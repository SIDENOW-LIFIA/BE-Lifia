package com.sidenow.domain.boardType.vote.board.dto.res;

import com.sidenow.domain.boardType.vote.board.entity.VoteBoard;
import com.sidenow.domain.boardType.vote.comment.entity.VoteBoardComment;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public abstract class VoteBoardResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(description = "투표게시판 게시글의 생성, 삭제, 업데이트 응답 객체")
    public static class VoteBoardCheck {

        @Schema(description = "투표게시판 게시글의 생성 여부 확인")
        private boolean saved;

        @Schema(description = "투표게시판 게시글의 삭제 여부 확인")
        private boolean deleted;

        @Schema(description = "투표게시판 게시글의 수정 여부 확인")
        private boolean updated;
    }
    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "단일 게시글 조회 응답 객체")
    public static class VoteBoardGetPostResponse {
        private final Long voteBoardPostId;
        private final Long memberId;
        private final String title;
        private final String content;
        private final String nickname;
        private final int hits;
        private final int likes;
        private final int commentsCount;
        private final String regDate;
        private final Map<String, String> files;
        private final List<VoteBoardComment> comments;

        public static VoteBoardGetPostResponse from(VoteBoard voteBoard, Map<String, String> files) {
            Member member = voteBoard.getMember();
            return VoteBoardGetPostResponse.builder()
                    .voteBoardPostId(voteBoard.getVoteBoardPostId())
                    .memberId(member.getMemberId())
                    .title(voteBoard.getTitle())
                    .content(voteBoard.getContent())
                    .nickname(member.getNickname())
                    .hits(voteBoard.getHits())
                    .likes(voteBoard.getLikes())
                    .commentsCount(voteBoard.getVoteBoardComments().size())
                    .regDate(voteBoard.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                    .files(files)
                    .comments(voteBoard.getVoteBoardComments())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "게시글 전체 조회 응답 객체")
    public static class VoteBoardGetPostListResponse {
        @Schema(description = "투표게시판 게시글 ID")
        private final Long voteBoardPostId;

        @Schema(description = "투표게시판 게시글 작성자 닉네임")
        private final String nickname;

        @Schema(description = "투표게시판 게시글 제목")
        private final String title;

        @Schema(description = "투표게시판 게시글 조회수")
        private final int hits;

        @Schema(description = "투표게시판 게시글 좋아요 수")
        private final int likes;

        @Schema(description = "투표게시판 게시글 댓글 수")
        private final int commentsCount;

        @Schema(description = "투표게시판 게시글 등록일자")
        private final String regDate;

        public static VoteBoardGetPostListResponse from(VoteBoard voteBoard) {
            return VoteBoardGetPostListResponse.builder()
                    .voteBoardPostId(voteBoard.getVoteBoardPostId())
                    .nickname(voteBoard.getMember().getNickname())
                    .title(voteBoard.getTitle())
                    .hits(voteBoard.getHits())
                    .likes(voteBoard.getLikes())
                    .commentsCount(voteBoard.getVoteBoardComments().size())
                    .regDate(voteBoard.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "투표게시판 게시글 전체 조회")
    public static class AllVoteBoards {
        private final List<VoteBoardGetPostListResponse> voteBoards;
    }

}
