package com.sidenow.domain.together.childcare.board.dto.res;

import com.sidenow.domain.together.childcare.board.entity.Childcare;
import com.sidenow.domain.together.childcare.comment.entity.ChildcareComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public abstract class ChildcareResponse {

    @Data
    @AllArgsConstructor
    @Schema(description = "육아해요 생성 응답 객체")
    public static class ChildcareCreateResponse {
        private Long id;
        private String title;
        private String content;
        private String image;

        public static ChildcareCreateResponse from(Childcare childcare) {
            return new ChildcareCreateResponse(childcare.getChildcareId(), childcare.getTitle(), childcare.getContent(), childcare.getImage());
        }
    }

    @Data
    @AllArgsConstructor
    @Schema(description = "육아해요 수정 응답 객체")
    public static class ChildcareUpdateResponse {
        private Long id;
        private String title;
        private String content;

        public static ChildcareUpdateResponse from(Childcare childcare) {
            return new ChildcareUpdateResponse(childcare.getChildcareId(), childcare.getTitle(), childcare.getContent());
        }
    }
    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "단일 게시글 조회 응답 객체")
    public static class ChildcareGetResponse {
        private final Long id;
        private final String title;
        private final String content;
        private final String image;
        private final String nickname;
        private final LocalDateTime createAt;
        private final int hits;
        private final int likes;
        private final int commentsCount;
        private final List<ChildcareComment> comments;

        public static ChildcareGetResponse from(Childcare childcare) {
            return ChildcareGetResponse.builder()
                    .id(childcare.getChildcareId())
                    .title(childcare.getTitle())
                    .content(childcare.getContent())
                    .image(childcare.getImage())
                    .nickname(childcare.getMember().getNickname())
                    .createAt(childcare.getCreatedAt())
                    .hits(childcare.getHits())
                    .likes(childcare.getLikes())
                    .commentsCount(childcare.getChildcareComments().size())
                    .comments(childcare.getChildcareComments())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "게시글 전체 조회 응답 객체")
    public static class ChildcareGetListResponse {
        @Schema(description = "육아해요 게시글 ID")
        private final Long childcarePostId;

        @Schema(description = "육아해요 게시글 작성자 닉네임")
        private final String nickname;

        @Schema(description = "육아해요 게시글 제목")
        private final String title;

        @Schema(description = "육아해요 게시글 조회수")
        private final int hits;

        @Schema(description = "육아해요 게시글 좋아요 수")
        private final int likes;

        @Schema(description = "육아해요 게시글 댓글 수")
        private final int commentsCount;

        @Schema(description = "육아해요 게시글 생성 일시")
        private final LocalDateTime createdAt;

        public static ChildcareGetListResponse from(Childcare childcare) {
            return ChildcareGetListResponse.builder()
                    .childcarePostId(childcare.getChildcareId())
                    .nickname(childcare.getMember().getNickname())
                    .title(childcare.getTitle())
                    .hits(childcare.getHits())
                    .likes(childcare.getLikes())
                    .commentsCount(childcare.getChildcareComments().size())
                    .createdAt(childcare.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "육아해요 게시글 전체 조회")
    public static class AllChildcares {
        private final List<ChildcareGetListResponse> childcares;
    }

}
