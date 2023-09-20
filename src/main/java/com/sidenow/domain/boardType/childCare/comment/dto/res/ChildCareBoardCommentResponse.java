package com.sidenow.domain.boardType.childCare.comment.dto.res;

import com.sidenow.domain.boardType.childCare.comment.entity.ChildCareBoardComment;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.format.DateTimeFormatter;

public abstract class ChildCareBoardCommentResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(description = "육아게시판 게시글의 댓글 생성, 삭제, 업데이트 응답 객체")
    public static class ChildCareBoardCommentCheck {

        @Schema(description = "육아게시판 게시글의 댓글 생성 여부 확인")
        private boolean saved;

        @Schema(description = "육아게시판 게시글의 댓글 삭제 여부 확인")
        private boolean deleted;

        @Schema(description = "육아게시판 게시글의 댓글 수정 여부 확인")
        private boolean updated;
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "육아게시판 게시글의 댓글 전체 조회 응답 객체")
    public static class ChildCareBoardGetCommentListResponse {
        private final Long childCareBoardCommentId;
        private final Long memberId;
        private final String nickname; // 작성자 별명
        private final String content; // 내용
        private final boolean isDeleted; // 삭제여부
        private final String reqDate; // 생성일시
//        private final Long parentId; // 부모 댓글
//        private final List<ReadChildCareBoardChildCommentResponse> children; // 자식댓글
//        private final int commentLikesCount; // 댓글 좋아요 개수

        public static ChildCareBoardGetCommentListResponse from(ChildCareBoardComment childCareBoardComments) {

            Member member = childCareBoardComments.getMember();

            return ChildCareBoardGetCommentListResponse.builder()
                    .nickname(member.getNickname())
                    .content(childCareBoardComments.getContent())
                    .reqDate(childCareBoardComments.getRegDate()
                            .format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                    .isDeleted(childCareBoardComments.getIsDeleted())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "육아게시판 게시글의 대댓글(자식) 상세 조회 응답 객체")
    public static class ReadChildCareBoardChildCommentResponse {
        private final String nickname;
        private final String content;
        private final boolean isDeleted;
        private final String regDate;
        private final Long parentId;

        public static ReadChildCareBoardChildCommentResponse from(ChildCareBoardComment childCareBoardComments) {
            Member member = childCareBoardComments.getMember();
            return ReadChildCareBoardChildCommentResponse.builder()
                    .nickname(member.getNickname())
                    .content(childCareBoardComments.getContent())
                    .regDate(childCareBoardComments.getRegDate()
                            .format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                    .isDeleted(childCareBoardComments.getIsDeleted())
                    .build();
        }
    }
}
