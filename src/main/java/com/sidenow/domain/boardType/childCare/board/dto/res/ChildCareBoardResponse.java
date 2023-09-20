package com.sidenow.domain.boardType.childCare.board.dto.res;

import com.sidenow.domain.boardType.childCare.board.entity.ChildCareBoard;
import com.sidenow.domain.boardType.childCare.comment.entity.ChildCareBoardComment;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public abstract class ChildCareBoardResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(description = "육아게시판 게시글의 생성, 삭제, 업데이트 응답 객체")
    public static class ChildCareBoardCheck {

        @Schema(description = "육아게시판 게시글의 생성 여부 확인")
        private boolean saved;

        @Schema(description = "육아게시판 게시글의 삭제 여부 확인")
        private boolean deleted;

        @Schema(description = "육아게시판 게시글의 수정 여부 확인")
        private boolean updated;
    }
    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "단일 게시글 조회 응답 객체")
    public static class ChildCareBoardGetPostResponse {
        private final Long childCareBoardPostId;
        private final Long memberId;
        private final String title;
        private final String content;
        private final String nickname;
        private final int hits;
        private final int likes;
        private final int commentsCount;
        private final String regDate;
        private final Map<String, String> files;
        private final List<ChildCareBoardComment> comments;

        public static ChildCareBoardGetPostResponse from(ChildCareBoard childCareBoard, Map<String, String> files) {
            Member member = childCareBoard.getMember();
            return ChildCareBoardGetPostResponse.builder()
                    .childCareBoardPostId(childCareBoard.getChildCareBoardPostId())
                    .memberId(member.getMemberId())
                    .title(childCareBoard.getTitle())
                    .content(childCareBoard.getContent())
                    .nickname(member.getNickname())
                    .hits(childCareBoard.getHits())
                    .likes(childCareBoard.getLikes())
                    .commentsCount(childCareBoard.getChildCareBoardComments().size())
                    .regDate(childCareBoard.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                    .files(files)
                    .comments(childCareBoard.getChildCareBoardComments())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "게시글 전체 조회 응답 객체")
    public static class ChildCareBoardGetPostListResponse {
        @Schema(description = "육아게시판 게시글 ID")
        private final Long childCareBoardPostId;

        @Schema(description = "육아게시판 게시글 작성자 닉네임")
        private final String nickname;

        @Schema(description = "육아게시판 게시글 제목")
        private final String title;

        @Schema(description = "육아게시판 게시글 조회수")
        private final int hits;

        @Schema(description = "육아게시판 게시글 좋아요 수")
        private final int likes;

        @Schema(description = "육아게시판 게시글 댓글 수")
        private final int commentsCount;

        @Schema(description = "육아게시판 게시글 등록일자")
        private final String regDate;

        public static ChildCareBoardGetPostListResponse from(ChildCareBoard childCareBoard) {
            return ChildCareBoardGetPostListResponse.builder()
                    .childCareBoardPostId(childCareBoard.getChildCareBoardPostId())
                    .nickname(childCareBoard.getMember().getNickname())
                    .title(childCareBoard.getTitle())
                    .hits(childCareBoard.getHits())
                    .likes(childCareBoard.getLikes())
                    .commentsCount(childCareBoard.getChildCareBoardComments().size())
                    .regDate(childCareBoard.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "육아게시판 게시글 전체 조회")
    public static class AllChildCareBoards {
        private final List<ChildCareBoardGetPostListResponse> childCareBoards;
    }

}
