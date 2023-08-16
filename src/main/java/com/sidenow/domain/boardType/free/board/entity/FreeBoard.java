package com.sidenow.domain.boardType.free.board.entity;

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
public class FreeBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId; // 게시글 고유 ID

    @Column(nullable = false)
    private String title; // 게시글 제목

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 게시글 내용

    @Column
    private String imageUrl;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int hits; // 게시글 조회 수

    @Column(nullable = false)
    private int likes; // 게시글 좋아요 수

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt; // 게시글 생성일자

    @UpdateTimestamp
    private LocalDateTime updatedAt; // 게시글 수정일자

    @OneToMany(mappedBy = "freeBoard")
    List<FreeBoardComment> freeBoardComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer; // 게시글 작성자

    public void increaseHits() { // 조회 수 증가
        this.hits++;
    }

    public void increaseLikes() { // 좋아요 수 증가
        this.likes++;
    }
}
