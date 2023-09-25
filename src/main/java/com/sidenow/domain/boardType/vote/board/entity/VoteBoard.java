package com.sidenow.domain.boardType.vote.board.entity;

import com.sidenow.domain.boardType.vote.comment.entity.VoteBoardComment;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
public class VoteBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_board_post_id")
    private Long voteBoardPostId; // 게시글 고유 ID

    @Column(nullable = false)
    private String title; // 게시글 제목

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 게시글 내용

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int hits; // 게시글 조회 수

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int likes; // 게시글 좋아요 수

    @Column(nullable = false)
    private String apartment; // 거주 아파트

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime regDate; // 게시글 생성일자

    @UpdateTimestamp
    private LocalDateTime updatedDate; // 게시글 수정일자

    // 게시글 삭제 시 첨부파일 모두 삭제
    @OneToMany(mappedBy = "voteBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteBoardFile> voteBoardFiles;

    // 게시글 삭제 시 달려있는 댓글 모두 삭제
    @OneToMany(mappedBy = "voteBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteBoardComment> voteBoardComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 게시글 작성자

    public void increaseHits() { // 조회 수 증가
        this.hits++;
    }

    public void increaseLikes() { // 좋아요 수 증가
        this.likes++;
    }
}
