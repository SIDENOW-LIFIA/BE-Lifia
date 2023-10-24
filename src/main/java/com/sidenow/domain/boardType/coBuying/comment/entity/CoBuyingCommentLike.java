package com.sidenow.domain.boardType.coBuying.comment.entity;

import com.sidenow.domain.member.entity.Member;
import com.sidenow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
public class CoBuyingCommentLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coBuying_comment_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coBuying_comment_id", nullable = false)
    private CoBuyingComment coBuyingComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public CoBuyingCommentLike(CoBuyingComment coBuyingComment, Member member) {
        this.coBuyingComment = coBuyingComment;
        this.member = member;
    }
}

