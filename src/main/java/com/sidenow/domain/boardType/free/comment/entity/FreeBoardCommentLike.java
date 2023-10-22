package com.sidenow.domain.boardType.free.comment.entity;

import com.sidenow.domain.member.entity.Member;
import com.sidenow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
public class FreeBoardCommentLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_board_comment_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_board_comment_id", nullable = false)
    private FreeBoardComment freeBoardComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public FreeBoardCommentLike(FreeBoardComment freeBoardComment, Member member) {
        this.freeBoardComment = freeBoardComment;
        this.member = member;
    }
}

