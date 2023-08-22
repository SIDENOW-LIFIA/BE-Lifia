package com.sidenow.domain.boardType.free.board.dto.res;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.comment.entity.FreeBoardComment;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class FreeBoardResponse {

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "게시글 등록 응답 객체")
    public static class CreateFreeBoardPostResponse {
        private final Long postId;
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "단일 게시글 조회 응답 객체")
    public static class ReadFreeBoardPostDetailResponse {
        private final String title;
        private final String content;
        private final String nickname;
        private final String imageUrl;
        private final int hits;
        private final int likes;
        private final int commentsCount;
        private final String createdAt;
        private final List<FreeBoardComment> comments;

        public static ReadFreeBoardPostDetailResponse from(FreeBoard freeBoard) {
            Member member = freeBoard.getMember();
            return ReadFreeBoardPostDetailResponse.builder()
                    .title(freeBoard.getTitle())
                    .content(freeBoard.getContent())
                    .nickname(member.getNickname())
                    .imageUrl(freeBoard.getImageUrl())
                    .hits(freeBoard.getHits())
                    .likes(freeBoard.getLikes())
                    .commentsCount(freeBoard.getFreeBoardComments().size())
                    .createdAt(freeBoard.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm")))
                    .comments(freeBoard.getFreeBoardComments())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "게시글 전체 조회 응답 객체")
    public static class ReadFreeBoardResponse {
        private final List<ReadFreeBoardResponse> freeBoards;
    }

}
