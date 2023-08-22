package com.sidenow.domain.boardType.free.comment.dto.res;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.comment.entity.FreeBoardComment;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class FreeBoardCommentResponse {

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "자유게시판 게시글의 댓글 생성 응답 객체")
    public static class CreateFreeBoardCommentResponse{

        private final FreeBoardComment freeBoardComment;
        private final Member member;
        private final FreeBoard freeBoardPost;
    }


    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "자유게시판 게시글의 댓글 전체 조회 응답 객체")
    public static class ReadFreeBoardCommentsAllResponse {
        private final int cnt; // 댓글 개수
        private final List<ReadFreeBoardCommentDetailResponse> freeBoardCommentList;
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "자유게시판 게시글의 댓글(부모) 상세 조회 응답 객체")
    public static class ReadFreeBoardCommentDetailResponse {
        private final Long freeBoardCommentId;
        private final Long memberId;
        private final String nickname; // 작성자 별명
        private final String content; // 내용
        private final boolean isDeleted; // 삭제여부
        private final String createdAt; // 생성일시
        private final Long parentId; // 부모 댓글
        private final List<ReadFreeBoardChildCommentResponse> children; // 자식댓글
        private final int commentLikesCount; // 댓글 좋아요 개수

        public static ReadFreeBoardCommentDetailResponse from(FreeBoardComment freeBoardComments, List<ReadFreeBoardChildCommentResponse> children) {

            Member member = freeBoardComments.getMember();

            return ReadFreeBoardCommentDetailResponse.builder()
                    .nickname(member.getNickname())
                    .content(freeBoardComments.getContent())
                    .createdAt(freeBoardComments.getCreatedAt()
                            .format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                    .parentId(freeBoardComments.getCommentId())
                    .isDeleted(freeBoardComments.getIsDeleted())
                    .children(children)
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "자유게시판 게시글의 대댓글(자식) 상세 조회 응답 객체")
    public static class ReadFreeBoardChildCommentResponse {
        private final String nickname;
        private final String content;
        private final boolean isDeleted;
        private final String createdAt;
        private final Long parentId;

        public static ReadFreeBoardChildCommentResponse from(FreeBoardComment freeBoardComments) {
            Member member = freeBoardComments.getMember();
            return ReadFreeBoardChildCommentResponse.builder()
                    .nickname(member.getNickname())
                    .content(freeBoardComments.getContent())
                    .createdAt(freeBoardComments.getCreatedAt()
                            .format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                    .isDeleted(freeBoardComments.getIsDeleted())
                    .build();
        }
    }

}
