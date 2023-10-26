package com.sidenow.domain.board.entity;

import com.sidenow.domain.comment.entity.Comment;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    @Lob
    private String content;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int hits; // 게시글 조회 수

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int commentCount; // 게시글 좋아요 수

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int likeCount; // 게시글 좋아요 수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member writer;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();

    public void updateTitle(String title) {
        this.title = title;
    }
    public void updateContent(String content) {
        this.content = content;
    }
    public void updateImage(String image) {
        this.image = image;
    }
    public void increaseHits() {
        this.hits++;
    }
    public void increaseLikes() {
        this.likeCount++;
    }
    public void decreaseLikes() {
        this.likeCount--;
    }
}
