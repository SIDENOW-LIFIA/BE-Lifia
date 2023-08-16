package com.sidenow.domain.boardType.free.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
public class FreeBoardComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId; // 댓글 고유 ID

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false) // 댓글의 게시글 (자유게시판)
    private FreeBoard freeBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member writer;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt; // 댓글 작성일자

    @UpdateTimestamp
    private LocalDateTime updatedAt; // 댓글 수정일자

    @ColumnDefault("FALSE")
    @Column(nullable = false)
    private Boolean isDeleted; // 댓글 삭제 여부

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private FreeBoardComment parent; // 부모 댓글

    @JsonManagedReference
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FreeBoardComment> children; // 자식 댓글 (부모 댓글 삭제돼도 삭제 안됨)

    // 부모 댓글 삭제 여부 확인
    public void changeIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    // 댓글 내용 수정
    public void updateContent(String content) {
        this.content = content;
    }
}