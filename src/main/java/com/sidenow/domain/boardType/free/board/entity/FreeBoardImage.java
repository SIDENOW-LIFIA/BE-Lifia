package com.sidenow.domain.boardType.free.board.entity;

import com.sidenow.domain.member.entity.Member;
import com.sidenow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor
@Entity
public class FreeBoardImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String newFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freeBoard_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FreeBoard freeBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;



    @Builder
    public FreeBoardImage(String originFileName, String newFileName, Member member, FreeBoard freeBoard) {
        super();
        this.originFileName = originFileName;
        this.newFileName = newFileName;
        this.member = member;
        this.freeBoard = freeBoard;
    }

    public void updateImg(String originFileName, String newFileName) {
        this.originFileName = originFileName;
        this.newFileName = newFileName;
    }
}
