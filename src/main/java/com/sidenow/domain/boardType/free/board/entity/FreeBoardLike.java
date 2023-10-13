package com.sidenow.domain.boardType.free.board.entity;

import com.sidenow.domain.member.entity.Member;
import com.sidenow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
public class FreeBoardLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freeBoard_id", nullable = false)
    private FreeBoard freeBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // 좋아요 누른 사용자가 제거되면 좋아요도 제거됨
    private Member member;

    public FreeBoardLike(FreeBoard freeBoard, Member member) {
        this.freeBoard = freeBoard;
        this.member = member;
    }
}
