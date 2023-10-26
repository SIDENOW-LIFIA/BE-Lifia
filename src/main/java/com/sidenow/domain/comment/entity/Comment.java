package com.sidenow.domain.comment.entity;

import com.sidenow.domain.board.entity.Post;
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
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id; // 댓글 고유 ID

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 댓글 내용

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int likeCount; // 댓글 좋아요 수

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDelete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "parent", orphanRemoval = false)
    private List<Comment> children = new ArrayList<>();

    @OneToMany(mappedBy = "comment")
    private List<CommentLike> commentLikes = new ArrayList<>();

    // 댓글 삭제 여부 확인
    public void changeIsDeleted(Boolean isDeleted) {
        this.isDelete = isDeleted;
    }

    // 댓글 내용 수정
    public void updateContent(String content) {
        this.content = content;
    }

    public void increaseLikes() {
        this.likeCount++;
    }

    public void decreaseLikes() {
        this.likeCount--;
    }
}
