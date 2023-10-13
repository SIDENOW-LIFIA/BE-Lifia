package com.sidenow.domain.boardType.free.board.entity;

import com.sidenow.domain.boardType.free.comment.entity.FreeBoardComment;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
public class FreeBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시글 고유 ID

    @Column(nullable = false)
    private String title; // 게시글 제목

    @Column(columnDefinition = "TEXT", nullable = false)
    @Lob // Large Object: 대용량 데이터가 들어가게 함
    private String content; // 게시글 내용

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int hits; // 게시글 조회 수

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int likes; // 게시글 좋아요 수

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime regDate; // 게시글 생성일자

    @UpdateTimestamp
    private LocalDateTime updatedDate; // 게시글 수정일자

    // 게시글 삭제 시 달려있는 댓글 모두 삭제
    @OneToMany(mappedBy = "freeBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FreeBoardComment> freeBoardComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // 작성자가 제거되면 게시글도 제거됨
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

    public void updateRegDate(LocalDateTime updatedDate){
        this.updatedDate = updatedDate;
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
