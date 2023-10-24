package com.sidenow.domain.boardType.vote.comment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sidenow.domain.boardType.vote.board.entity.Vote;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
public class VoteComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId; // 댓글 고유 ID

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 댓글 내용

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int likes; // 댓글 좋아요 수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id", nullable = false) // 댓글의 게시글 (투표해요)
    private Vote vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Boolean isDeleted; // 댓글 삭제 여부

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private VoteComment parent; // 부모 댓글

    // 부모 댓글 삭제 시 남아있는 자식 댓글 모두 삭제
    @JsonManagedReference
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteComment> children;

    // 댓글 좋아요
    @JsonIgnore // 무한 순환 참조 방지
    @OneToMany(mappedBy = "voteComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteCommentLike> voteCommentLikes;

    // 댓글 삭제 여부 확인
    public void changeIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    // 댓글 내용 수정
    public void updateContent(String content) {
        this.content = content;
    }

    public void increaseLikes() {
        this.likes++;
    }

    public void decreaseLikes() {
        this.likes--;
    }
}
