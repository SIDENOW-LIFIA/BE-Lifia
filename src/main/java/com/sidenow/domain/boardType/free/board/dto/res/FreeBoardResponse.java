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
    @Setter
    @NoArgsConstructor
    @Schema(description = "자유게시판 게시글의 생성, 삭제, 업데이트 응답 객체")
    public static class FreeBoardCheck {

        @Schema(description = "자유게시판 게시글의 생성 여부 확인")
        private boolean saved;

        @Schema(description = "자유게시판 게시글의 삭제 여부 확인")
        private boolean deleted;

        @Schema(description = "자유게시판 게시글의 수정 여부 확인")
        private boolean updated;
    }
    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "단일 게시글 조회 응답 객체")
    public static class FreeBoardGetPostResponse {
        private final String title;
        private final String content;
        private final String nickname;
        private final String imageUrl;
        private final int hits;
        private final int likes;
        private final int commentsCount;
        private final String createdAt;
        private final List<FreeBoardComment> comments;

        public static FreeBoardGetPostResponse from(FreeBoard freeBoard) {
            Member member = freeBoard.getMember();
            return FreeBoardGetPostResponse.builder()
                    .title(freeBoard.getTitle())
                    .content(freeBoard.getContent())
                    .nickname(member.getNickname())
                    .hits(freeBoard.getHits())
                    .likes(freeBoard.getLikes())
                    .commentsCount(freeBoard.getFreeBoardComments().size())
                    .createdAt(freeBoard.getRegDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm")))
                    .comments(freeBoard.getFreeBoardComments())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "게시글 전체 조회 응답 객체")
    public static class FreeBoardGetPostListResponse {
        @Schema(description = "자유게시판 게시글 ID")
        private final Long freeBoardPostId;

        @Schema(description = "자유게시판 게시글 작성자 닉네임")
        private final String nickname;

        @Schema(description = "자유게시판 게시글 제목")
        private final String title;

        @Schema(description = "자유게시판 게시글 조회수")
        private final int hits;

        @Schema(description = "자유게시판 게시글 등록일자")
        private final String regDate;
    }

}
