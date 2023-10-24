package com.sidenow.domain.boardType.vote.comment.dto.res;

import com.sidenow.domain.boardType.vote.comment.entity.VoteComment;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

public abstract class VoteCommentResponse {

    @Data
    @AllArgsConstructor
    @Schema(description = "투표해요 게시글 댓글 생성 응답 객체")
    public static class VoteCommentCreateResponse {
        private Long id;
        private Member writer;
        private String content;

        public static VoteCommentCreateResponse from(VoteComment voteComment) {
            return new VoteCommentCreateResponse(voteComment.getCommentId(), voteComment.getMember(), voteComment.getContent());
        }
    }

    @Data
    @AllArgsConstructor
    @Schema(description = "투표해요 게시글 댓글 수정 응답 객체")
    public static class VoteCommentUpdateResponse {
        private Long id;
        private Member writer;
        private String content;

        public static VoteCommentUpdateResponse from(VoteComment voteComment) {
            return new VoteCommentUpdateResponse(voteComment.getCommentId(), voteComment.getMember(), voteComment.getContent());
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "투표해요 게시글의 댓글 전체 조회 응답 객체")
    public static class VoteGetCommentListResponse {
        private final Long id;
        private final Long parentId;
        private final String nickname; // 작성자 별명
        private final String content; // 내용
        private final LocalDateTime createdAt; // 생성일시

        public static VoteGetCommentListResponse from(VoteComment voteComments) {
            Member member = voteComments.getMember();

            return VoteGetCommentListResponse.builder()
                    .id(voteComments.getCommentId())
                    .nickname(member.getNickname())
                    .content(voteComments.getContent())
                    .createdAt(voteComments.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "투표해요 게시글의 대댓글(자식) 상세 조회 응답 객체")
    public static class ReadVoteChildCommentResponse {
        private final String nickname;
        private final String content;
        private final boolean isDeleted;
        private final LocalDateTime createdAt;
        private final Long parentId;

        public static ReadVoteChildCommentResponse from(VoteComment voteComments) {
            Member member = voteComments.getMember();
            return ReadVoteChildCommentResponse.builder()
                    .nickname(member.getNickname())
                    .content(voteComments.getContent())
                    .createdAt(voteComments.getCreatedAt())
                    .isDeleted(voteComments.getIsDeleted())
                    .build();
        }
    }
}
