package com.sidenow.domain.boardType.free.comment.dto.res;

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
        private final Long freeBoardComment;
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
    @Schema(description = "자유게시판 게시글의 댓글 상세 조회 응답 객체")
    public static class ReadFreeBoardCommentDetailResponse {
        private final String nickname;
        private final String content;
        private final boolean isDeleted;
        private final String createdAt;
        private final Long parentId;
        private final List<ReadFreeBoardChildCommentResponse> children;

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
    @Schema(description = "자유게시판 게시글의 댓글 상세 조회 응답 객체")
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
