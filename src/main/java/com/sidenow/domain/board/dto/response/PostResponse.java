package com.sidenow.domain.board.dto.response;

import com.sidenow.domain.board.entity.Post;
import com.sidenow.domain.comment.entity.Comment;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public abstract class PostResponse {


    @Builder
    @Schema(description = "게시글 생성 응답 객체")
    public static class PostCreateResponse {
        private Long id;
        private String title;
        private String content;
        private String image;
        private Member writer;

        public static PostCreateResponse from(Post post){
            return PostCreateResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .image(post.getImage())
                    .writer(post.getWriter())
                    .build();
        }
    }

    @Getter
    @Builder
    @Schema(description = "게시판 목록에서 보여지는 심플한 게시글 응답 객체")
    public static class PostSimpleResponse {
        private Long id;
        private String title;
        private String nickname;
        private int hits;
        private int likeCount;
        private int commentCount;
        private LocalDateTime createdAt;

        public static PostSimpleResponse from(Post post) {
            return PostSimpleResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .nickname(post.getWriter().getNickname())
                    .hits(post.getHits())
                    .likeCount(post.getLikeCount())
                    .commentCount(post.getCommentCount())
                    .createdAt(post.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Schema(description = "게시글 상세 조회 응답 객체")
    public static class PostDetailResponse {
        private Long id;
        private String title;
        private String content;
        private String image;
        private String nickname;
        private int hits;
        private int likeCount;
        private int commentCount;
        private List<Comment> comments;
        private LocalDateTime createdAt;

        public static PostDetailResponse from(Post post) {
            return PostDetailResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .image(post.getImage())
                    .nickname(post.getWriter().getNickname())
                    .hits(post.getHits())
                    .likeCount(post.getLikeCount())
                    .commentCount(post.getCommentCount())
                    .comments(post.getComments())
                    .createdAt(post.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Schema(description = "게시글 전체 조회 응답 객체")
    public static class AllPosts{
        private List<PostSimpleResponse> posts;
    }
}
