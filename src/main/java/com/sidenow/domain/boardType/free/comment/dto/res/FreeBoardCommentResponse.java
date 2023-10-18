package com.sidenow.domain.boardType.free.comment.dto.res;

import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse;
import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.comment.entity.FreeBoardComment;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

public abstract class FreeBoardCommentResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(description = "자유게시판 게시글의 댓글 생성, 삭제, 업데이트 응답 객체")
    public static class FreeBoardCommentCheck {

        @Schema(description = "자유게시판 게시글의 댓글 생성 여부 확인")
        private boolean saved;

        @Schema(description = "자유게시판 게시글의 댓글 삭제 여부 확인")
        private boolean deleted;

        @Schema(description = "자유게시판 게시글의 댓글 수정 여부 확인")
        private boolean updated;
    }

    @Data
    @AllArgsConstructor
    @Schema(description = "자유게시판 게시글 댓글 생성 응답 객체")
    public static class FreeBoardCommentCreateResponse {
        private Long id;
        private Member writer;
        private String content;

        public static FreeBoardCommentCreateResponse from(FreeBoardComment freeBoardComment) {
            return new FreeBoardCommentCreateResponse(freeBoardComment.getCommentId(), freeBoardComment.getMember(), freeBoardComment.getContent());
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "자유게시판 게시글의 댓글 전체 조회 응답 객체")
    public static class FreeBoardGetCommentListResponse {
        private final Long freeBoardCommentId;
        private final Long memberId;
        private final String nickname; // 작성자 별명
        private final String content; // 내용
        private final boolean isDeleted; // 삭제여부
        private final String reqDate; // 생성일시
//        private final Long parentId; // 부모 댓글
//        private final List<ReadFreeBoardChildCommentResponse> children; // 자식댓글
//        private final int commentLikesCount; // 댓글 좋아요 개수

        public static FreeBoardGetCommentListResponse from(FreeBoardComment freeBoardComments) {

            Member member = freeBoardComments.getMember();

            return FreeBoardGetCommentListResponse.builder()
                    .nickname(member.getNickname())
                    .content(freeBoardComments.getContent())
//                    .reqDate(freeBoardComments.getRegDate()
//                            .format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                    .isDeleted(freeBoardComments.getIsDeleted())
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
        private final LocalDateTime createdAt;
        private final Long parentId;

        public static ReadFreeBoardChildCommentResponse from(FreeBoardComment freeBoardComments) {
            Member member = freeBoardComments.getMember();
            return ReadFreeBoardChildCommentResponse.builder()
                    .nickname(member.getNickname())
                    .content(freeBoardComments.getContent())
                    .createdAt(freeBoardComments.getCreatedAt())
                    .isDeleted(freeBoardComments.getIsDeleted())
                    .build();
        }
    }
}
