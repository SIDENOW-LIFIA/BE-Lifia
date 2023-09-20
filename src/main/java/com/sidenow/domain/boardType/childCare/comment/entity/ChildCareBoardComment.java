package com.sidenow.domain.boardType.childCare.comment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sidenow.domain.boardType.childCare.board.entity.ChildCareBoard;
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
public class ChildCareBoardComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId; // 댓글 고유 ID

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "childCare_board_post_id", nullable = false) // 댓글의 게시글 (육아게시판)
    private ChildCareBoard childCareBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime regDate; // 댓글 작성일자

    @UpdateTimestamp
    private LocalDateTime updatedAt; // 댓글 수정일자

    @Column(nullable = false)
    private Boolean isDeleted; // 댓글 삭제 여부

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ChildCareBoardComment parent; // 부모 댓글

    @JsonManagedReference
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChildCareBoardComment> children; // 자식 댓글 (부모 댓글 삭제돼도 삭제 안됨)

    // 댓글 좋아요
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "childCareBoardComment", cascade = CascadeType.REMOVE, orphanRemoval = true)

    // 댓글 삭제 여부 확인
    public void changeIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    // 댓글 내용 수정
    public ChildCareBoardComment updateContent(String content) {
        this.content = content;
        return this;
    }
}
