package com.sidenow.domain.freeboard.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sidenow.domain.freeboard.comment.entity.FreeBoardComment;
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
public class FreeBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_board_id")
    private Long freeBoardId; // 게시글 고유 ID

    @Column(nullable = false)
    private String title; // 게시글 제목

    @Column(columnDefinition = "TEXT", nullable = false)
    @Lob // Large Object: 대용량 데이터가 들어가게 함
    private String content; // 게시글 내용

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column
    private String openLink;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int hits; // 게시글 조회 수

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int likes; // 게시글 좋아요 수

    // 게시글 삭제 시 달려있는 댓글 모두 삭제
    @JsonIgnore // 무한 순환 참조 방지
    @OneToMany(mappedBy = "freeBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FreeBoardComment> freeBoardComments;

    // 게시글 삭제 시 달려있는 좋아요 모두 삭제
    @JsonIgnore // 무한 순환 참조 방지
    @OneToMany(mappedBy = "freeBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FreeBoardLike> freeBoardLikes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 게시글 작성자

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
        this.likes++;
    }

    public void decreaseLikes() {
        this.likes--;
    }
}